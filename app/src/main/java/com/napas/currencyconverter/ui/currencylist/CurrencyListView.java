package com.napas.currencyconverter.ui.currencylist;

import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.ui.base.MvpView;

import java.util.List;

public interface CurrencyListView extends MvpView {

    void showCurrencyList(List<Currency> currencies);

    void showSelectedItem(Currency currency);

}
