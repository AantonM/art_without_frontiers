package com.cet325.bg69xx;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class CurrenciesUnitTest {

    @Test
        public void testGetOfflineCurrency_differentCurrencies(){
        double expectedRate = 1.1932;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("eur.json");

        CurrencyHttpClient ticketsActivity = new CurrencyHttpClient("EUR");
        double actualRate = ticketsActivity.getOfflineCurrencyRate(inputStream,"USD");

        assertEquals(expectedRate,actualRate,0);
    }

    @Test
    public void testGetOfflineCurrency_sameCurrencies(){
        double expectedRate = 0.0;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("eur.json");

        CurrencyHttpClient ticketsActivity = new CurrencyHttpClient("EUR");
        double actualRate = ticketsActivity.getOfflineCurrencyRate(inputStream,"EUR");

        assertEquals(expectedRate,actualRate,0);
    }
}