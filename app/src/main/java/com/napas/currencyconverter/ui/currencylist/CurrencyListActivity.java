package com.napas.currencyconverter.ui.currencylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.napas.currencyconverter.R;
import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class CurrencyListActivity extends BaseActivity implements CurrencyListView {

    @Inject
    CurrencyListPresenter<CurrencyListView> mPresenter;

    @BindView(R.id.rv_currency_list)
    RecyclerView rvCurrencyList;

    public static final String KEY_CURRENCY = "currency";
    private CurrencyListAdapter mCurrencyListAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CurrencyListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        initToolbar(getString(R.string.select_currency), true);
        mPresenter.onAttach(this);
        mPresenter.onInitialize();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @OnTextChanged(R.id.et_search)
    public void onSearchTextChanged(CharSequence input) {
        if (mCurrencyListAdapter == null) return;
        mCurrencyListAdapter.getFilter().filter(input.toString().trim());
    }

    @Override
    public void showCurrencyList(List<Currency> currencies) {
        mCurrencyListAdapter = new CurrencyListAdapter(this, currencies);
        mCurrencyListAdapter.getPositionClicks().subscribe(new Consumer<Currency>() {
            @Override
            public void accept(@NonNull Currency currency) throws Exception {
                showSelectedItem(currency);
            }
        });
        rvCurrencyList.setLayoutManager(new LinearLayoutManager(this));
        rvCurrencyList.setAdapter(mCurrencyListAdapter);
    }

    @Override
    public void showSelectedItem(Currency currency) {
        Intent data = new Intent();
        data.putExtra(KEY_CURRENCY, currency);
        setResult(RESULT_OK, data);
        finish();
    }
}
