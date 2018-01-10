package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class TicketsActivity extends BaseFrameActivity {

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


    private double checkCurrency() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultCurrency = getString(R.string.default_currency);
        String currentCurrency = preferences.getString("pref_currency", "");
        double currentCurrencyRates = 1.0;

        if(!currentCurrency.equals(defaultCurrency))
        {
            CurrencyHttpClient getCurrencyRatesRequest = new CurrencyHttpClient(defaultCurrency);
            currentCurrencyRates = getCurrencyRatesRequest.getCurrencyRate(currentCurrency);
        }

        Toast.makeText(this, "The rate: " + defaultCurrency + "-" + currentCurrency + " is: " + currentCurrencyRates, Toast.LENGTH_SHORT).show();
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

        //get and calculate adult ticket price
        double adultTicketPrice = Double.valueOf(getString(R.string.adult_ticket_price)) * currencyRate;
        double defaultChildPercentageDiscount = Double.valueOf(getString(R.string.child_discount_percentage));
        double childTicketPrice = calculateDiscountTicketPrice(defaultChildPercentageDiscount,adultTicketPrice);
        double defaultStudentPercentageDiscount = Double.valueOf(getString(R.string.student_discount_percentage));
        double studentTicketPrice = calculateDiscountTicketPrice(defaultStudentPercentageDiscount, adultTicketPrice);

        txtAdultTicketPrice.setText(String.valueOf(round(adultTicketPrice)));
        txtChildTicketPrice.setText(String.valueOf(round(childTicketPrice)));
        txtStudentTicketPrice.setText(String.valueOf(round(studentTicketPrice)));
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
