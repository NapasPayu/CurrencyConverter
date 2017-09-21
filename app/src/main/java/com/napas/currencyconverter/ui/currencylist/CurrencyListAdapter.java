package com.napas.currencyconverter.ui.currencylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.napas.currencyconverter.R;
import com.napas.currencyconverter.data.model.Currency;
import com.napas.currencyconverter.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.ItemViewHolder> implements Filterable {

    private Context mContext;
    private List<Currency> mCurrencies = null;
    private List<Currency> mFilteredCurrencies = null;
    private ItemFilter mFilter = new ItemFilter();
    private final PublishSubject<Currency> onClickSubject = PublishSubject.create();

    public CurrencyListAdapter(Context context, List<Currency> currencies) {
        mContext = context;
        mCurrencies = currencies;
        mFilteredCurrencies = currencies;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int pos) {
        final Currency currency = mFilteredCurrencies.get(pos);
        if (currency == null) return;

        CommonUtils.loadCircleImage(mContext, currency.getFullFlagPath(), holder.ivFlag);
        holder.tvCurrency.setText(currency.getCode() + " - " + currency.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(currency);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredCurrencies.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public List<Currency> getFilteredCurrencies() {
        return mFilteredCurrencies;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_flag)
        ImageView ivFlag;
        @BindView(R.id.tv_currency)
        TextView tvCurrency;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            if (filterString.isEmpty()) {
                mFilteredCurrencies = mCurrencies;
            } else {
                ArrayList<Currency> filteredCurrencies = new ArrayList<>();
                for (Currency currency : mCurrencies) {
                    String currencyKeyword = currency.getCode() + currency.getCountry() + currency.getName();
                    if (currencyKeyword.toLowerCase().contains(filterString)) {
                        filteredCurrencies.add(currency);
                    }
                }
                mFilteredCurrencies = filteredCurrencies;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mFilteredCurrencies;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredCurrencies = (ArrayList<Currency>) results.values;
            notifyDataSetChanged();
        }
    }

    public PublishSubject<Currency> getPositionClicks() {
        return onClickSubject;
    }
}
