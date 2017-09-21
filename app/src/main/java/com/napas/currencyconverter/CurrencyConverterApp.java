package com.napas.currencyconverter;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.napas.currencyconverter.data.DataManager;
import com.napas.currencyconverter.di.component.ApplicationComponent;
import com.napas.currencyconverter.di.component.DaggerApplicationComponent;
import com.napas.currencyconverter.di.module.ApplicationModule;

import javax.inject.Inject;

public class CurrencyConverterApp extends Application {

    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        AndroidNetworking.initialize(getApplicationContext());
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
