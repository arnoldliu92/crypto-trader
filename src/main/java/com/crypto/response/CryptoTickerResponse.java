package com.crypto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"high", "open", "count", "vol", "low", "bidQty", "askSize", "askQty", "bidSize", "close", "amount"})
public class CryptoTickerResponse {
    private String symbol;
    private double bidPrice;
    private double askPrice;
}
