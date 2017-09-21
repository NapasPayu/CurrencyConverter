package com.napas.currencyconverter.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.napas.currencyconverter.R;
import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.data.model.Exchange;
import com.napas.currencyconverter.data.model.Exchanges;
import com.napas.currencyconverter.ui.base.BaseActivity;
import com.napas.currencyconverter.ui.currencylist.CurrencyListActivity;
import com.napas.currencyconverter.ui.custom.AmountEditText;
import com.napas.currencyconverter.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.napas.currencyconverter.R.string.currency;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter<MainView> mPresenter;

    @BindView(R.id.tv_base_rate)
    TextView tvBaseRate;
    @BindView(R.id.iv_base_flag)
    ImageView ivBaseFlag;
    @BindView(R.id.tv_base_code)
    TextView tvBaseCode;
    @BindView(R.id.et_base_amount)
    AmountEditText etBaseAmount;
    @BindView(R.id.tv_target_rate)
    TextView tvTargetRate;
    @BindView(R.id.iv_target_flag)
    ImageView ivTargetFlag;
    @BindView(R.id.tv_target_code)
    TextView tvTargetCode;
    @BindView(R.id.et_target_amount)
    AmountEditText etTargetAmount;

    private static final int BASE_REQUEST_CODE = 1;
    private static final int TARGET_REQUEST_CODE = 2;
    private String mBaseCode, mTargetCode;
    private Double mBaseExchangeRate, mTargetExchangeRate;
    private boolean isBaseAmountEnabled = true;
    private boolean isTargetAmountEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        initToolbar(getString(currency), false);
        mPresenter.onAttach(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        Currency currency = data.getParcelableExtra(CurrencyListActivity.KEY_CURRENCY);
        setCurrency(requestCode, currency);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.ll_base_currency)
    public void onBaseCurrencyClicked() {
        startActivityForResult(CurrencyListActivity.getStartIntent(this), BASE_REQUEST_CODE);
    }

    @OnClick(R.id.ll_target_currency)
    public void onTargetCurrencyClicked() {
        startActivityForResult(CurrencyListActivity.getStartIntent(this), TARGET_REQUEST_CODE);
    }

    @OnTextChanged(R.id.et_base_amount)
    public void onBaseAmountChanged() {
        if (!isBaseAmountEnabled || mBaseExchangeRate == null) return;

        updateTargetAmount();
    }

    @OnTextChanged(R.id.et_target_amount)
    public void onTargetAmountChanged() {
        if (!isTargetAmountEnabled || mTargetExchangeRate == null) return;

        updateBaseAmount();
    }

    @Override
    public void setCurrency(int requestCode, Currency currency) {
        if (currency == null) return;

        // set currency info
        String code = currency.getCode();
        String flagPath = currency.getFullFlagPath();
        if (requestCode == BASE_REQUEST_CODE) {
            mBaseCode = code;
            CommonUtils.loadCircleImage(this, flagPath, ivBaseFlag);
            tvBaseCode.setText(code);
        } else if (requestCode == TARGET_REQUEST_CODE) {
            mTargetCode = code;
            CommonUtils.loadCircleImage(this, flagPath, ivTargetFlag);
            tvTargetCode.setText(code);
        }

        if (TextUtils.isEmpty(mBaseCode) || TextUtils.isEmpty(mTargetCode)) return;
        // call get exchange api when both base currency and target currency are selected
        mPresenter.onCurrencySelect(mBaseCode, mTargetCode);
    }

    @Override
    public void setExchangeRates(Exchanges exchanges) {
        Exchange baseExchange = exchanges.getBaseExchange();
        Exchange targetExchange = exchanges.getTargetExchange();
        mBaseExchangeRate = baseExchange.getRate();
        mTargetExchangeRate = targetExchange.getRate();
        tvBaseRate.setText(getString(R.string.exchange_rate, baseExchange.getBaseCode(), Double.toString(mBaseExchangeRate), baseExchange.getTargetCode()));
        tvTargetRate.setText(getString(R.string.exchange_rate, targetExchange.getBaseCode(), Double.toString(mTargetExchangeRate), targetExchange.getTargetCode()));

        showAmountInputs();
        updateTargetAmount();
    }

    @Override
    public void showAmountInputs() {
        if (etBaseAmount.getVisibility() == View.GONE) {
            etBaseAmount.setVisibility(View.VISIBLE);
        }
        if (etTargetAmount.getVisibility() == View.GONE) {
            etTargetAmount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateBaseAmount() {
        double targetAmount = etTargetAmount.getAmount();
        double baseAmount = targetAmount * mTargetExchangeRate;
        isBaseAmountEnabled = false;
        etBaseAmount.setText(CommonUtils.formatAmount(baseAmount));
        isBaseAmountEnabled = true;
    }

    @Override
    public void updateTargetAmount() {
        double baseAmount = etBaseAmount.getAmount();
        double targetAmount = baseAmount * mBaseExchangeRate;
        isTargetAmountEnabled = false;
        etTargetAmount.setText(CommonUtils.formatAmount(targetAmount));
        isTargetAmountEnabled = true;
    }

}
