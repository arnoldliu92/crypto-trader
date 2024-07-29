package com.crypto.entity;

import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Trade {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_type")
    private long userId;

    @Column(name = "trade_type")
    private TradeType tradeType;

    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private double amount;

    @Column(name = "timestamp_created")
    private Timestamp timestampCreated;

}
