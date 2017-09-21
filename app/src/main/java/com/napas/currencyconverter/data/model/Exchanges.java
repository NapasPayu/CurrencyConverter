package com.napas.currencyconverter.data.model;

public class Exchanges {

    private Exchange baseExchange;
    private Exchange targetExchange;

    public Exchanges(Exchange baseExchange, Exchange targetExchange) {
        this.baseExchange = baseExchange;
        this.targetExchange = targetExchange;
    }

    public Exchange getBaseExchange() {
        return baseExchange;
    }

    public void setBaseExchange(Exchange baseExchange) {
        this.baseExchange = baseExchange;
    }

    public Exchange getTargetExchange() {
        return targetExchange;
    }

    public void setTargetExchange(Exchange targetExchange) {
        this.targetExchange = targetExchange;
    }
}
