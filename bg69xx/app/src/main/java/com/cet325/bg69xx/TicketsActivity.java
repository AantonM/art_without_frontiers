package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;

public class TicketsActivity extends BaseFrameActivity {

    private String currentCurrency;

    /***
     * Method called when this activity is created that set up the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_tickets);
        getSupportActionBar().setTitle("Admission");
        super.setupDrawer();

        //get the currency from the preferences
        double currencyRate = checkCurrency();

        //display tickets price
        displayTicketPrices(currencyRate);
    }

    @Override
    protected void onResume () {
        super.onResume();

        //get the currency from the preferences
        double currencyRate = checkCurrency();

        //display tickets price
        displayTicketPrices(currencyRate);
    }

    /***
     * Check the value of the ticket by the local currency.
     * @return
     */
    private double checkCurrency() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultCurrency = getString(R.string.default_currency);
        currentCurrency = preferences.getString("pref_currency", "");
        //default currency rate if the currency we try to exchange the currency to itself. E.g. EUR->EUR = 1.00
        double currentCurrencyRates = 1.0;

        //get currency from API
        if(!currentCurrency.equals(defaultCurrency))
        {
            TextView offlineMsg = (TextView) findViewById(R.id.offlineText);
            CurrencyHttpClient getCurrencyRatesRequest = new CurrencyHttpClient(defaultCurrency);
            currentCurrencyRates = getCurrencyRatesRequest.getOnlineCurrencyRate(currentCurrency);
            offlineMsg.setVisibility(View.INVISIBLE);

            //if the API returns 0.0 (offline or down) use predefined currency rates
            if(currentCurrencyRates == 0.0){
                int jsonFileId = this.getResources().getIdentifier(defaultCurrency.toLowerCase(), "raw", this.getPackageName());
                currentCurrencyRates = getCurrencyRatesRequest.getOfflineCurrencyRate(getResources().openRawResource(jsonFileId),currentCurrency);

                offlineMsg.setVisibility(View.VISIBLE);
            }
        }
        return currentCurrencyRates;
    }

    private void calculateTicketPriceCurrencyRate() {
    }

    /***
     * Method that add the ticket price values to the views
     * @param currencyRate
     */
    private void displayTicketPrices(double currencyRate) {

        //get views
        TextView txtAdultTicketPrice = (TextView)findViewById(R.id.txtAdult);
        TextView txtChildTicketPrice = (TextView)findViewById(R.id.txtChild);
        TextView txtStudentTicketPrice = (TextView)findViewById(R.id.txtStudent);

        int currencySignID = this.getResources().getIdentifier(currentCurrency.toLowerCase(), "string", this.getPackageName());
        String currencySign = getString(currencySignID);

        //get and calculate adult ticket price
        double adultTicketPrice = Double.valueOf(getString(R.string.adult_ticket_price)) * currencyRate;
        double defaultChildPercentageDiscount = Double.valueOf(getString(R.string.child_discount_percentage));
        double childTicketPrice = calculateDiscountTicketPrice(defaultChildPercentageDiscount,adultTicketPrice);
        double defaultStudentPercentageDiscount = Double.valueOf(getString(R.string.student_discount_percentage));
        double studentTicketPrice = calculateDiscountTicketPrice(defaultStudentPercentageDiscount, adultTicketPrice);

        txtAdultTicketPrice.setText(String.valueOf(round(adultTicketPrice)) + " " +currencySign);
        txtChildTicketPrice.setText(String.valueOf(round(childTicketPrice))+ " " +currencySign);
        txtStudentTicketPrice.setText(String.valueOf(round(studentTicketPrice))+ " " +currencySign);
    }

    private double round(double number){
        return Math.round(number * 100)/100;
    }

    /***
     * Calculate the ticket price according to the ticket type and the discount allocated to this type.
     *
     * @param percentage_discount
     * @return
     */
    private double calculateDiscountTicketPrice(double percentage_discount, double adult_ticket_price) {
        return Double.valueOf(adult_ticket_price - ((percentage_discount * adult_ticket_price)/100));
    }

}
