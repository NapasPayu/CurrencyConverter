package com.napas.currencyconverter.data.model;

//{
//        "code": "CHF",
//        "country": "Switzerland",
//        "name": "Swiss Franc",
//        "flagPath": "/flags/ch.png",
//        "rate": 1.0369
//}

import android.os.Parcel;
import android.os.Parcelable;

import com.napas.currencyconverter.BuildConfig;

public class Currency implements Parcelable {

    private String code;
    private String country;
    private String name;
    private String flagPath;
    private Double rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagPath() {
        return flagPath;
    }

    public void setFlagPath(String flagPath) {
        this.flagPath = flagPath;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getFullFlagPath() {
        return BuildConfig.BASE_URL + flagPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.country);
        dest.writeString(this.name);
        dest.writeString(this.flagPath);
        dest.writeValue(this.rate);
    }

    public Currency() {
    }

    protected Currency(Parcel in) {
        this.code = in.readString();
        this.country = in.readString();
        this.name = in.readString();
        this.flagPath = in.readString();
        this.rate = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}
