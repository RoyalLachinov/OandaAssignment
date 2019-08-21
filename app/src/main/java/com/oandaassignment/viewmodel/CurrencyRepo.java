package com.oandaassignment.viewmodel;

import android.util.Log;
import com.oandaassignment.domain.api.ApiClient;
import com.oandaassignment.domain.api.CurrencyDao;
import com.oandaassignment.domain.model.CurrencyParent;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CurrencyRepo {

    private static CurrencyRepo currencyRepo;

    public static CurrencyRepo getInstance() {
        if (currencyRepo == null) {
            currencyRepo = new CurrencyRepo();
        }
        return currencyRepo;
    }

    public void getCurrencyNames(CurrencyDao currencyDao) {
        //MutableLiveData<CurrencyParent> currencyNamesMutableLiveData = new MutableLiveData<>();

        ApiClient.getApiService().getCurrencyNames().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CurrencyParent>() {
                    @Override
                    public void onCompleted() {
                        Log.e("eeeee", "compeleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("eeeee", e.getMessage());
                        //currencyNamesMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onNext(CurrencyParent currencyParent) {
                        if (currencyParent != null) {
                            for (int i = 0; i < currencyParent.getCurrencies().size(); i++) {
                                currencyDao.insertCurrency(currencyParent.getCurrencies().get(i));
                            }
                            //currencyNamesMutableLiveData.postValue(currencyParent);
                            Log.e("eeeee", "successfull");
                        }
                    }
                });
        //return currencyNamesMutableLiveData;
    }

    public void getCurrencyPrices(CurrencyDao currencyDao) {

        //MutableLiveData<CurrencyParent> currencyPricesMutableLiveData = new MutableLiveData<>();

        ApiClient.getApiService().getCurrencyPrices().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CurrencyParent>() {
                    @Override
                    public void onCompleted() {
                        Log.e("eeeee", "compeleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("eeeee", e.getMessage());
                        //currencyPricesMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onNext(CurrencyParent currencyParent) {

                        if (currencyParent != null) {
                            for (int i = 0; i < currencyParent.getCurrencies().size(); i++) {

                                currencyDao.updateCurrency(currencyParent.getCurrencies().get(i).getIsoCode(),currencyParent.getCurrencies().get(i).getPrice());

                                if (currencyParent.getCurrencies().get(i).getIsoCode().equals("CAD") ||
                                        currencyParent.getCurrencies().get(i).getIsoCode().equals("USD")) {

                                    //currencyPricesMutableLiveData.postValue(currencyParent);

                                }
                            }
                            Log.e("eeeee", "successfull");
                        }
                    }
                });

        //return currencyPricesMutableLiveData;
    }


}
