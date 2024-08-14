package com.crypto.exception;

import com.crypto.enums.CryptoType;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(Long userId, CryptoType cryptoType, BigDecimal requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %d. CryptoType: %s, Requested amount: %,.2f\"",
                userId, cryptoType, requestedAmount));
    }
    public InsufficientBalanceException(Long userId, CryptoType cryptoType, BigDecimal currentBalance, BigDecimal requestedAmount) {
        super(String.format("Insufficient Crypto balance for user %d. CryptoType: %s, Current balance: %,.2f, Requested amount: %,.2f",
                userId, cryptoType.toString(), currentBalance, requestedAmount));
    }
}
