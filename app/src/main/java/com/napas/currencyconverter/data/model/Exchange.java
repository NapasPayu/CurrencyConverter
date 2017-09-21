package com.napas.currencyconverter.data.model;

//{
//        "baseCode": "CNY",
//        "targetCode": "MYR",
//        "rate": 0.64201677
//}

public class Exchange {

    private String baseCode;
    private String targetCode;
    private Double rate;

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
