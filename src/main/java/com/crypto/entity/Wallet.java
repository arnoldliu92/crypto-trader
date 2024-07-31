package com.crypto.entity;

import com.crypto.enums.CryptoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "wallet_id")
    private long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id")
    private long userId;

    @NotNull(message = "Crypto Type is required")
    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @NotNull(message = "Balance is required")
    @Column(name = "balance")
    private double balance;

    @ElementCollection
    @CollectionTable(name = "crypto_balances", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "crypto_type")
    @Column(name = "balance")
    private Map<CryptoType, Double> cryptoBalances = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
