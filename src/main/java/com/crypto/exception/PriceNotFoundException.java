package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(CryptoType cryptoType) {
        super(String.format("Price of %s couldn't be found, cache or DB is unavailable", cryptoType.toString()));
    }
}
