package com.oandaassignment.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oandaassignment.R;
import com.oandaassignment.databinding.ActivityCalculateBinding;
import com.oandaassignment.domain.model.Currency;
import com.oandaassignment.viewmodel.CurrencyViewModel;

import java.util.List;


public class CalculateActivity extends AppCompatActivity {

    private ActivityCalculateBinding binding;
    private CurrencyViewModel currencyViewModel;
    private Boolean edit_text_changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currencyViewModel = ViewModelProviders.of(CalculateActivity.this).get(CurrencyViewModel.class);
        currencyViewModel.init();

        binding = DataBindingUtil.setContentView(CalculateActivity.this, R.layout.activity_calculate);
        binding.setViewModel(currencyViewModel);

        // Filling of source and destination currencies
        currencyViewModel.getCurrencyNames().observe(CalculateActivity.this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(@Nullable List<Currency> currencyList) {
                List<Currency> currencyArrayList = currencyList;

                Currency currency = new Currency();
                if (currencyArrayList.isEmpty()) {
                    currency.setIsoCode("No Item");
                    currencyArrayList.add(currency);
                } else {
                    currency.setIsoCode("Choose");
                    currencyArrayList.set(0, currency);
                }

                ArrayAdapter<Currency> adapter = new ArrayAdapter<Currency>(CalculateActivity.this, android.R.layout.simple_spinner_item, currencyArrayList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_medium));
                        textView.setPadding(20, 0, 0, 0);
                        return textView;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerSource.setAdapter(adapter);
                binding.spinnerDestination.setAdapter(adapter);

            }
        });

        //Source currency select listener
        currencyViewModel.getSourceUpdatedCurrency().observe(CalculateActivity.this, currency -> {

            if (currency.getPrice() != 0.0) {
                binding.textSourceNameAndPrice.setText("1 USD = "+String.format("%.2f", currency.getPrice()) +"  "+currency.getName());
            }

            //Source price change listener
            calculateDestinationPrice();

        });

        //Destination currency select listener
        currencyViewModel.getDestinationUpdatedCurrency().observe(CalculateActivity.this, currency -> {

            if (currency.getPrice() != 0.0) {
                binding.textDestinationNameAndPrice.setText("1 USD = "+String.format("%.2f", currency.getPrice()) +"  "+currency.getName());
            }

            //Destination price change listener
            calculateSourcePrice();
        });

        //Source price change listener
        currencyViewModel.getSourceEditTexValue().observe(CalculateActivity.this, aDouble -> {

            calculateDestinationPrice();

        });

        //Destination price change listener
        currencyViewModel.getDestinationEditTexValue().observe(CalculateActivity.this, aDouble -> {

            //Source price change listener
            calculateSourcePrice();
        });
    }

    private void calculateDestinationPrice() {
        Currency selectedItem1 = (Currency) binding.spinnerSource.getSelectedItem();
        Currency selectedItem2 = (Currency) binding.spinnerDestination.getSelectedItem();

        if (!edit_text_changed) {
            edit_text_changed = true;
            double input = binding.editTextSource.getText().toString().length() > 0 ? Double.parseDouble(binding.editTextSource.getText().toString()) : 0;

            if (selectedItem2.getPrice() == 0.0) {
                binding.editTextDestination.setText(0.0 + "");
            } else {
                binding.editTextDestination.setText(currencyViewModel.getSourcePrice(input, selectedItem1.getPrice(), selectedItem2.getPrice()));
            }
            edit_text_changed = false;
        }
    }

    private void calculateSourcePrice() {
        Currency selectedItem1 = (Currency) binding.spinnerSource.getSelectedItem();
        Currency selectedItem2 = (Currency) binding.spinnerDestination.getSelectedItem();

        if (!edit_text_changed) {
            edit_text_changed = true;

            double input = binding.editTextDestination.getText().toString().length() > 0 ? Double.parseDouble(binding.editTextDestination.getText().toString()) : 0;
            if (selectedItem1.getPrice() == 0.0) {
                binding.editTextSource.setText(0.0 + "");
            } else {
                binding.editTextSource.setText(currencyViewModel.getDestinationPrice(input, selectedItem1.getPrice(), selectedItem2.getPrice()));
            }

            edit_text_changed = false;
        }
    }
}
