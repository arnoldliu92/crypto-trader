package com.crypto.response;

import lombok.Data;

@Data
public class CryptoNestedTickerResponse {
    private CryptoTickerResponse[] cryptoTickerResponses;
}
