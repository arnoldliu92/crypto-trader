package com.crypto.entity;

import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "trade_id")
    private long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id")
    private long userId;

    @NotNull(message = "Trade Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type")
    private TradeType tradeType;

    @NotNull(message = "Crypto Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @NotNull(message = "Price is required")
    @Column(name = "price")
    private double price;

    @NotNull(message = "Amount is required")
    @Column(name = "amount")
    private double amount;

    @CreationTimestamp
    @Column(name = "timestamp_created")
    private Timestamp timestampCreated;

    public Trade(long userId, TradeType tradeType, CryptoType cryptoType, double price, double amount, Timestamp timestampCreated) {
        this.userId = userId;
        this.tradeType = tradeType;
        this.cryptoType = cryptoType;
        this.price = price;
        this.amount = amount;
        this.timestampCreated = timestampCreated;
    }
}
