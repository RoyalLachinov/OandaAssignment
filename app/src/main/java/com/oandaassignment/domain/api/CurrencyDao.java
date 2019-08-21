package com.oandaassignment.domain.api;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.oandaassignment.domain.model.Currency;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Query("Select * From Currency Order by isoCode")
    LiveData<List<Currency>> getAllCurrencies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrency(Currency currency);

    @Query("UPDATE Currency SET price = :price WHERE isoCode = :isoCode")
    int updateCurrency(String isoCode, double price);

}
