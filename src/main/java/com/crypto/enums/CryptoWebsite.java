package com.crypto.enums;

public enum CryptoWebsite {
    BINANCE("https://api.binance.com/api/v3/ticker/bookTicker"),
    HUOBI("https://api.huobi.pro/market/tickers");


    private final String apiUrl;

    CryptoWebsite(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
