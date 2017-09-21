package com.napas.currencyconverter.data.network;

import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.data.model.Exchange;

import java.util.List;

import io.reactivex.Observable;

public interface ApiHelper {

    Observable<List<Currency>> getAllCurrencies();

    Observable<Exchange> getExchangeRate(String baseCode, String targetCode);

}
