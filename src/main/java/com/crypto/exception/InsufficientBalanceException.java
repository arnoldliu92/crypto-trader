package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(Long userId, Double currentBalance, Double requestedAmount) {
        super(String.format("Insufficient Cash balance for user %d. Current balance: %,.2f, Requested amount: %,.2f",
                userId, currentBalance, requestedAmount));
    }
    public InsufficientBalanceException(Long userId, CryptoType cryptoType, Double currentBalance, Double requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %d. CryptoType: %s, Current balance: %,.2f, Requested amount: %,.2f",
                userId, cryptoType.toString(), currentBalance, requestedAmount));
    }
}
