package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends BaseFrameActivity {

    private String[] currencies;
    private String defaultCurrency;
    private boolean spinnerIsIncrediblyStupidAndFiresTheListenerDuringInitialisationWithoutHumanInteractionFFS = false;

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

        //get all currencies from xml
        getCurrencies();

        //get default curency
        getDefaultCurrency();

        //add records to the spinner
        populateCurrencySpinner();
    }

    private void getDefaultCurrency() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        defaultCurrency = preferences.getString("currency", "");
    }

    /***
     * Get currencies form xml file and save them into array string
     */
    private void getCurrencies() {
        currencies = getResources().getStringArray(R.array.currency);
    }

    /***
     * Method that add options to the currency spinner from the string-array:currency.
     */
    private void populateCurrencySpinner() {
        List<String> spinnerArray = Arrays.asList(currencies);

        Spinner currencySpinner = (Spinner) findViewById(R.id.currency_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        currencySpinner.setAdapter(adapter);
        //select the default currency
        currencySpinner.setSelection(Arrays.asList(currencies).indexOf(defaultCurrency));
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spinnerIsIncrediblyStupidAndFiresTheListenerDuringInitialisationWithoutHumanInteractionFFS){
                    spinnerIsIncrediblyStupidAndFiresTheListenerDuringInitialisationWithoutHumanInteractionFFS = true;
                }else{
                    updateCurrency(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    private void updateCurrency(int currencyId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("currency", currencies[currencyId]);
        editor.apply();
        Toast.makeText(this, "Shared pref created: ", Toast.LENGTH_SHORT).show();
    }


}
