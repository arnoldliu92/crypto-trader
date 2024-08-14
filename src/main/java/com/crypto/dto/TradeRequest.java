package com.crypto.dto;

import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TradeRequest {
    private TradeType tradeType;
    private CryptoType cryptoType;
    private BigDecimal amount;
}
