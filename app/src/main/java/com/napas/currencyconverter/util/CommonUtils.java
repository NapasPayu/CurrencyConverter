package com.napas.currencyconverter.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.napas.currencyconverter.ui.custom.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class CommonUtils {

    public static void loadCircleImage(Context context, String imageUrl, ImageView imageView) {
        if (TextUtils.isEmpty(imageUrl)) return;

        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerInside()
                .transform(new CropCircleTransformation())
                .into(imageView);

        if (imageView.getVisibility() == View.GONE) imageView.setVisibility(View.VISIBLE);
    }

    public static String formatAmount(Double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);
    }
}
