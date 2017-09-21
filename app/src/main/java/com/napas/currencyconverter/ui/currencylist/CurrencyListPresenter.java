package com.napas.currencyconverter.ui.currencylist;

import com.napas.currencyconverter.ui.base.MvpPresenter;

public interface CurrencyListPresenter<V extends CurrencyListView> extends MvpPresenter<V> {

    void onInitialize();

}