package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException() {
        super("User does not have the required wallet.");
    }

    public WalletNotFoundException(CryptoType cryptoType) {
        super("User does not have the required wallet.");
    }

}
