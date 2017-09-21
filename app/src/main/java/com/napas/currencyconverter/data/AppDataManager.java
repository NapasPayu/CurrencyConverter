package com.napas.currencyconverter.data;

import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.data.model.Exchange;
import com.napas.currencyconverter.data.network.ApiHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper) {
        mApiHelper = apiHelper;
    }

    @Override
    public Observable<List<Currency>> getAllCurrencies() {
        return mApiHelper.getAllCurrencies();
    }

    @Override
    public Observable<Exchange> getExchangeRate(String baseCode, String targetCode) {
        return mApiHelper.getExchangeRate(baseCode, targetCode);
    }
}
