package com.crypto.entity;

import com.crypto.enums.CryptoType;
import com.crypto.enums.DataSource;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Price {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "source")
    private DataSource dataSource;

    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @Column(name = "bid_price")
    private double bidPrice;

    @Column(name = "ask_price")
    private double askPrice;

    @Column(name = "timestamp")
    private Timestamp timestampCreated;

}
