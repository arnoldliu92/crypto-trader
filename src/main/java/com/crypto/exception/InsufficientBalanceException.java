package com.crypto.exception;

import com.crypto.enums.CryptoType;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(Long userId, CryptoType cryptoType, Double requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %d. CryptoType: %s, Requested amount: %,.2f\"",
                userId, cryptoType, requestedAmount));
    }
    public InsufficientBalanceException(Long userId, CryptoType cryptoType, Double currentBalance, Double requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %d. CryptoType: %s, Current balance: %,.2f, Requested amount: %,.2f",
                userId, cryptoType.toString(), currentBalance, requestedAmount));
    }
}
