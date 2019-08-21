package com.oandaassignment.domain.api;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.oandaassignment.domain.model.Currency;

@Database(entities = {Currency.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "currency_db").build();
        }

        return INSTANCE;
    }

    public abstract CurrencyDao getCurrencyDao();

}
