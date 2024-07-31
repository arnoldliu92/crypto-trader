package com.crypto.entity;

import com.crypto.enums.CryptoType;
import com.crypto.enums.DataSource;
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
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "price_id")
    private long id;

    @NotNull(message = "Data Source is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private DataSource dataSource;

    @NotNull(message = "Crypto Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @Column(name = "bid_price")
    private double bidPrice;

    @Column(name = "ask_price")
    private double askPrice;

    @CreationTimestamp
    @Column(name = "timestamp")
    private Timestamp timestampCreated;
}
