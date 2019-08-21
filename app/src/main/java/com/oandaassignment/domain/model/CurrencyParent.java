package com.oandaassignment.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrencyParent {

    @SerializedName("currencies")
    ArrayList<Currency> currencies;

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }
}
