package com.napas.currencyconverter.ui.main;

import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.data.model.Exchanges;
import com.napas.currencyconverter.ui.base.MvpView;

public interface MainView extends MvpView {

    void setCurrency(int requestCode, Currency currency);

    void setExchangeRates(Exchanges exchanges);

    void showAmountInputs();

    void updateBaseAmount();

    void updateTargetAmount();
}
