package com.springboot.example.currencyconverter.controllers;

import com.springboot.example.currencyconverter.model.CurrencySingleton;
import com.springboot.example.currencyconverter.services.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/currency-converter")
public class CurrencyConverterController {

    private final CurrencyService currencyService;

    public CurrencyConverterController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/")
    public String welcomePage() {
        return "/currency-converter/welcome-page";
    }

    @GetMapping(value = "/select-currencies")
    public String selectCurrencies(Model model) {
        Set<String> currencies = CurrencySingleton.getInstance().getCurrencies().keySet();
        model.addAttribute("currencies", currencies);

        return "/currency-converter/select-currencies";
    }

    @PostMapping(value = "/converted-currency")
    public String convertedCurrency(@RequestParam("currency_from") String currencyFrom,
                                    @RequestParam("currency_to") String currencyTo,
                                    @RequestParam("count") Double count,
                                    Model model) {
        model.addAttribute("cash", currencyService.converted(currencyFrom, currencyTo, count));
        model.addAttribute("currency", currencyTo);

        return "/currency-converter/result";
    }
}