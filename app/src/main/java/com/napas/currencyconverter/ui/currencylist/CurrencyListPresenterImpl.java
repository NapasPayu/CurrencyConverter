package com.napas.currencyconverter.ui.currencylist;

import com.napas.currencyconverter.R;
import com.napas.currencyconverter.data.DataManager;
import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.ui.base.BasePresenter;
import com.napas.currencyconverter.util.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class CurrencyListPresenterImpl<V extends CurrencyListView> extends BasePresenter<V> implements CurrencyListPresenter<V> {

    @Inject
    public CurrencyListPresenterImpl(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onInitialize() {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAllCurrencies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<List<Currency>>() {
                    @Override
                    public void onNext(List<Currency> currencies) {
                        getMvpView().showCurrencyList(currencies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().showOkDialog(R.string.error, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                }));
    }
}
