package com.cet325.bg69xx;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by MitkovA on 09/01/2018.
 */

public class ResponseCurrencyRateMapper {

    private String base;
    private String date;

    private Map<String, String> rates = new TreeMap<String, String>();

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
