package com.napas.currencyconverter.ui.main;

import com.napas.currencyconverter.R;
import com.napas.currencyconverter.data.DataManager;
import com.napas.currencyconverter.data.model.Exchange;
import com.napas.currencyconverter.data.model.Exchanges;
import com.napas.currencyconverter.ui.base.BasePresenter;
import com.napas.currencyconverter.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

public class MainPresenterImpl<V extends MainView> extends BasePresenter<V> implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCurrencySelect(String baseCode, String targetCode) {
        getMvpView().showLoading();

        Observable<Exchange> baseGetExchangeObservable = getDataManager().getExchangeRate(baseCode, targetCode);
        Observable<Exchange> targetGetExchangeObservable = getDataManager().getExchangeRate(targetCode, baseCode);
        getCompositeDisposable().add(Observable.zip(baseGetExchangeObservable, targetGetExchangeObservable,
                new BiFunction<Exchange, Exchange, Exchanges>() {
                    @Override
                    public Exchanges apply(@NonNull Exchange baseExchange, @NonNull Exchange targetExchange) throws Exception {
                        return new Exchanges(baseExchange, targetExchange);
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<Exchanges>() {
                                   @Override
                                   public void onNext(Exchanges exchanges) {
                                       getMvpView().setExchangeRates(exchanges);
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
                               }
                ));
    }
}
