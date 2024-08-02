package com.crypto.response;

import com.crypto.enums.CryptoType;
import lombok.Data;

@Data
public class CryptoTickerResponse {
    private CryptoType symbol;
    private double bidPrice;
    private double askPrice;
}
