package com.springboot.example.currencyconverter.services;

import com.springboot.example.currencyconverter.model.CurrencySingleton;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;

@Data
@Service
public class CurrencyService {
    public double converted(final String currencyFrom, final String currencyTo, double count) {
        Map<String, Double> currencies = CurrencySingleton.getInstance().getCurrencies();

        return count * currencies.get(currencyFrom) / currencies.get(currencyTo);
    }

}