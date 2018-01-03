package com.cet325.bg69xx;

import android.os.Bundle;
import android.widget.TextView;

public class TicketsActivity extends HomeActivity {

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
        getSupportActionBar().setTitle("Admission");
        setContentView(R.layout.activity_tickets);
        super.setupDrawer();
        
        displayTicketPrices();
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
