package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class TicketsActivity extends BaseFrameActivity {

    private static final int ADULT_TICKET_PRICE = 20;
    private static final int CHILD_TICKET_PERCENTAGE_DISCOUNTED = 70;
    private static final int STUDENT_TICKET_PERCENTAGE_DISCOUNTED = 50;


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
        getCurrency();

        calculateTicketPriceCurrencyRate();

        //display tickets price
        displayTicketPrices();
    }


    private void getCurrency() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("currency", "");
        Toast.makeText(this, "Shared pref is: " + name, Toast.LENGTH_SHORT).show();
    }


    private void calculateTicketPriceCurrencyRate() {
    }

    /***
     * Method that add the ticket price values to the views
     */
    private void displayTicketPrices() {

        TextView txtAdultTicketPrice = (TextView)findViewById(R.id.txtAdult);
        txtAdultTicketPrice.setText(String.valueOf(ADULT_TICKET_PRICE));

        TextView txtChildTicketPrice = (TextView)findViewById(R.id.txtChild);
        txtChildTicketPrice.setText(String.valueOf(calculateTicketPrice(CHILD_TICKET_PERCENTAGE_DISCOUNTED)));

        TextView txtStudentTicketPrice = (TextView)findViewById(R.id.txtStudent);
        txtStudentTicketPrice.setText(String.valueOf(calculateTicketPrice(STUDENT_TICKET_PERCENTAGE_DISCOUNTED)));
    }

    /***
     * Calculate the ticket price according to the ticket type and the discount allocated to this type.
     *
     * @param percentage_discount
     * @return
     */
    private int calculateTicketPrice(int percentage_discount) {
        return ADULT_TICKET_PRICE - ((percentage_discount * ADULT_TICKET_PRICE)/100);
    }

}
