package com.cet325.bg69xx;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CurrencyHttpClient {

    private static final String BASE_URL = "http://api.fixer.io/";
    private static String FULL_URL;
    private  ResponseCurrencyRateMapper response;

    public CurrencyHttpClient(String base) {
        FULL_URL = BASE_URL + "/latest?base=" + base;

        DownloadData data = new DownloadData();

        //Wait until the data is received from the API
        try {
            Object result = data.execute().get();
            Log.d("API connection status:", "Getting data completed.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    public double getCurrencyRate(String currencyCode){

        if(response != null){
            String rate = response.getRates().get(currencyCode);
            return Double.valueOf((rate != null)?rate:"0.0");
        }

        return 0;
    }

    public double getOfflineCurrencyRate(InputStream stream, String currentCurrency){
        StringBuffer buffer = new StringBuffer();
        Gson gson = new Gson();

        try {

            int data = stream.read();
            while (data != -1) {
                buffer.append((char) data);
                data = stream.read();
            }

            stream.close();
            response = gson.fromJson(buffer.toString(), ResponseCurrencyRateMapper.class);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IOException:", e.getMessage());
        }

        double currencyRate = getCurrencyRate(currentCurrency);

        return currencyRate;
    }


    class DownloadData extends AsyncTask<Void, Void, ResponseCurrencyRateMapper> {
        @Override
        protected ResponseCurrencyRateMapper doInBackground(Void... voids) {
            StringBuffer buffer = new StringBuffer();
            Gson gson = new Gson();
            try {
                URL url = new URL(FULL_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                int data = stream.read();

                while (data != -1) {
                    buffer.append((char) data);
                    data = stream.read();
                }

                stream.close();

                response = gson.fromJson(buffer.toString(), ResponseCurrencyRateMapper.class);

            } catch (MalformedURLException e) {

                e.printStackTrace();
                Log.d("MalformedURLException:", e.getMessage());

            } catch (IOException e) {

                e.printStackTrace();
                Log.d("IOException:", e.getMessage());
            }
            return response;
        }
    }

}
