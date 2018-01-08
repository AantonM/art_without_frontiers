    package com.cet325.bg69xx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class SettingsActivity extends HomeActivity {

    //TODO: save settings to file

    /***
     * Method called when this activity is created that set up the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

        //add records to the spinner
        populateCurrencySpinner();
    }

    /***
     * Method that add options to the currency spinner from the string-array:currency.
     *
     */
    private void populateCurrencySpinner() {
        List<String> spinnerArray =  Arrays.asList(getResources().getStringArray(R.array.currency));

        Spinner currencySpinner = (Spinner) findViewById(R.id.currency_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        currencySpinner.setAdapter(adapter);
    }


}
