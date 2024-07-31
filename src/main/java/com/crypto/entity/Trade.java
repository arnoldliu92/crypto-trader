package com.crypto.entity;

import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "trade_id")
    private long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id")
    private long userId;

    @NotNull(message = "Trade Type is required")
    @Column(name = "trade_type")
    private TradeType tradeType;

    @NotNull(message = "Crypto Type is required")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
