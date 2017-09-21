package com.napas.currencyconverter.di.module;

import android.app.Activity;
import android.content.Context;

import com.napas.currencyconverter.di.ActivityContext;
import com.napas.currencyconverter.di.PerActivity;
import com.napas.currencyconverter.ui.currencylist.CurrencyListPresenter;
import com.napas.currencyconverter.ui.currencylist.CurrencyListPresenterImpl;
import com.napas.currencyconverter.ui.currencylist.CurrencyListView;
import com.napas.currencyconverter.ui.main.MainPresenter;
import com.napas.currencyconverter.ui.main.MainPresenterImpl;
import com.napas.currencyconverter.ui.main.MainView;
import com.napas.currencyconverter.util.rx.AppSchedulerProvider;
import com.napas.currencyconverter.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl<MainView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CurrencyListPresenter<CurrencyListView> provideCurrencyListPresenter(CurrencyListPresenterImpl<CurrencyListView> presenter) {
        return presenter;
    }

}
