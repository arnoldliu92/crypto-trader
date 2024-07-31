package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException() {
        super("User does not have the required wallet.");
    }

    public WalletNotFoundException(Long userId, CryptoType cryptoType) {
        super(String.format("User %l does not have the required %s wallet.", userId, cryptoType.toString()));
    }

}
