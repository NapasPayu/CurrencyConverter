package com.napas.currencyconverter.ui.main;

import com.napas.currencyconverter.ui.base.MvpPresenter;

public interface MainPresenter<V extends MainView> extends MvpPresenter<V> {

    void onCurrencySelect(String baseCode, String targetCode);

}
