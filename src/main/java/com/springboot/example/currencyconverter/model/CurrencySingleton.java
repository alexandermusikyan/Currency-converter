package com.springboot.example.currencyconverter.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CurrencySingleton {
    private static final String JSON_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    private static final String JSON_FIELD_DATE = "Date";
    private static final String JSON_FIELD_VALUTE = "Valute";
    private static CurrencySingleton INSTANCE;

    private String date;
    private Map<String, Double> currencies;
    private CurrencySingleton() {}
    @SneakyThrows
    private void initialize() {

        ObjectMapper mapper = new ObjectMapper();
        URL www = new URL(JSON_URL);

        JsonNode jsonNode = mapper.readTree(www);

        date = jsonNode.get(JSON_FIELD_DATE).asText();
        currencies = StreamSupport.stream(jsonNode.get(JSON_FIELD_VALUTE).spliterator(), false)
                .map(value -> {
            try {
                return mapper.treeToValue(value, Currency.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toMap(Currency::getName, currency -> currency.getValue() / currency.getNominal()));

        //currencies.forEach(currency -> System.out.println(new String(currency.getName().getBytes(), Charset.forName("Cp1251"))));
    }

    public static CurrencySingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CurrencySingleton();
            INSTANCE.initialize();
        }

        return INSTANCE;
    }

    public Map<String, Double> getCurrencies() {
        return currencies;
    }

    public String getDate() {
        return date;
    }
}