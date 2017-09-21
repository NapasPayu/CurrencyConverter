package com.napas.currencyconverter.data.network;

import com.google.gson.reflect.TypeToken;
import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.data.model.Exchange;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    public AppApiHelper() {
    }

    @Override
    public Observable<List<Currency>> getAllCurrencies() {
        return Rx2AndroidNetworking.get(ApiEndPoint.CURRENCY)
                .build()
                .getParseObservable(new TypeToken<List<Currency>>() {
                });
    }

    @Override
    public Observable<Exchange> getExchangeRate(String baseCode, String targetCode) {
        return Rx2AndroidNetworking.get(ApiEndPoint.EXCHANGE)
                .addQueryParameter("baseCode", baseCode)
                .addQueryParameter("targetCode", targetCode)
                .build()
                .getParseObservable(new TypeToken<Exchange>() {
                });
    }
}

