package com.crypto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoNestedTickerResponse {
    private CryptoTickerResponse[] data;
}
