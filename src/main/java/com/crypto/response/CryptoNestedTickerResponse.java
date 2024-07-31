package com.crypto.response;

import lombok.Data;

@Data
public class CryptoNestedTickerResponse {
    public CryptoTickerResponse[] cryptoTickerResponses;
}
