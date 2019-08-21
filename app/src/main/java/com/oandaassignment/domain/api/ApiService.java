package com.oandaassignment.domain.api;


import com.oandaassignment.domain.model.CurrencyParent;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("names.json")
    Observable<CurrencyParent> getCurrencyNames();

    @GET("prices.json")
    Observable<CurrencyParent> getCurrencyPrices();

}
