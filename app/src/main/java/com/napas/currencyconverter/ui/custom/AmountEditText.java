package com.napas.currencyconverter.ui.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * EditText for Amount.
 * Amount is formatted like 999,990.00 to Max 8 digits
 */

public class AmountEditText extends AppCompatEditText {

    private static final String PATTERN = "#,##0.00";  //e.g 999,990.00
    //    private static final int MAX_LENGTH = 14;
    private static final String replaceable = "[.,]";

    private String current = "";
    private DecimalFormat decimalFormat;
    private boolean isInitialized = false;

    public AmountEditText(Context context) {
        super(context);
        init();
    }

    public AmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public AmountEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initialize defaults
     */
    private void init() {
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setSingleLine(false);
//        setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});

        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        decimalFormat.applyPattern(PATTERN);
        isInitialized = true;
        setText("0.00");
        setSelection(getText().length());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (isInitialized && !text.toString().equals(current)) {

            //get input
            String originalInput = text.toString();

            //clean input
            String cleanInput = originalInput.replaceAll(replaceable, "");

            //double input
            double doubleInput = 0.0d;
            try {
                doubleInput = Double.parseDouble(cleanInput);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            doubleInput = doubleInput / 100d;
            String formatted = decimalFormat.format(doubleInput);
            current = formatted;

            //set to the editText
            setText(formatted);
            setSelection(formatted.length());
        }
    }

    public Double getAmount() {
        String input = getText().toString();
        if (TextUtils.isEmpty(input)) {
            return 0.00;
        } else {
            return Double.valueOf(input.replaceAll(",", ""));
        }
    }
}
