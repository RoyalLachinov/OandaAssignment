package com.oandaassignment.viewmodel;

import android.app.Application;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oandaassignment.R;
import com.oandaassignment.domain.api.AppDatabase;
import com.oandaassignment.domain.model.Currency;

import java.util.List;


public class CurrencyViewModel extends AndroidViewModel {


    private AppDatabase appDatabase;
    private LiveData<List<Currency>> currencyLiveData;
    private MutableLiveData<Currency> sourceCurrencyLiveData = new MutableLiveData<>();
    private MutableLiveData<Currency> destinationCurrencyLiveData = new MutableLiveData<>();
    private MutableLiveData<Double> sourceEditTexValue = new MutableLiveData<>();
    private MutableLiveData<Double> destinationEditTexValue = new MutableLiveData<>();
    private CurrencyRepo currencyRepo;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDataBase(getApplication());
        currencyLiveData = appDatabase.getCurrencyDao().getAllCurrencies();
    }

    public void init() {
        if (currencyLiveData != null) {
            return;
        }
        currencyRepo = CurrencyRepo.getInstance();
        currencyRepo.getCurrencyNames(appDatabase.getCurrencyDao());
        currencyRepo.getCurrencyPrices(appDatabase.getCurrencyDao());

    }

    public LiveData<List<Currency>> getCurrencyNames() {
        return currencyLiveData;
    }

    public LiveData<Currency> getSourceUpdatedCurrency() {
        return sourceCurrencyLiveData;
    }

    public LiveData<Currency> getDestinationUpdatedCurrency() {
        return destinationCurrencyLiveData;
    }

    public String getSourcePrice(double value, double sourcePrice, double destinationPrice) {
        return String.valueOf((value / sourcePrice) * destinationPrice);
    }

    public String getDestinationPrice(double value, double destinationPrice, double sourcePrice) {
        return String.valueOf((value / sourcePrice) * destinationPrice);
    }

    public void onSourceSelectItem(AdapterView<?> parent, View view, int position, long id) {
        sourceCurrencyLiveData.setValue((Currency)parent.getSelectedItem());
        ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_PX, view.getContext().getResources().getDimension(R.dimen.text_size_medium));
        (parent.getChildAt(0)).setPadding(20, 0, 0, 0);
    }

    public void onDestinationSelectItem(AdapterView<?> parent, View view, int position, long id) {
        destinationCurrencyLiveData.setValue((Currency)parent.getSelectedItem());
        ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_PX, view.getContext().getResources().getDimension(R.dimen.text_size_medium));
        (parent.getChildAt(0)).setPadding(20, 0, 0, 0);
    }

    public LiveData<Double>getSourceEditTexValue(){
        return sourceEditTexValue;
    }

    public LiveData<Double>getDestinationEditTexValue(){
        return destinationEditTexValue;
    }

    public void onSourceTextChanged(CharSequence s, int start, int before, int count) {
        if(!String.valueOf(s).trim().trim().equals("")){
            sourceEditTexValue.setValue(Double.parseDouble(s.toString().trim()));
        }else{
            sourceEditTexValue.setValue(Double.parseDouble("0.0"));
        }

    }

    public void onDestinationTextChanged(CharSequence s, int start, int before, int count) {

        if(!String.valueOf(s).trim().trim().equals("")){
            destinationEditTexValue.setValue(Double.parseDouble(s.toString().trim()));
        }else{
            destinationEditTexValue.setValue(Double.parseDouble("0.0"));
        }
    }
}