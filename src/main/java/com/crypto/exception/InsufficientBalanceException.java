package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(Long userId, Double currentBalance, Double requestedAmount) {
        super(String.format("Insufficient Cash balance for user %l. Current balance: %d, Requested amount: %d",
                userId, currentBalance, requestedAmount));
    }
    public InsufficientBalanceException(Long userId, CryptoType cryptoType, Double currentBalance, Double requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %l. CryptoType: %s, Current balance: %d, Requested amount: %d",
                userId, cryptoType.toString(), currentBalance, requestedAmount));
    }
}
