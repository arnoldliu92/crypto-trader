package com.crypto.entity;

import com.crypto.enums.CryptoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "wallet_id")
    private long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id")
    private long userId;

    @NotNull(message = "Crypto Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "crypto_type")
    private CryptoType cryptoType;

    @NotNull(message = "Balance is required")
    @Column(name = "balance")
    private double balance;

    public Wallet(long userId, CryptoType cryptoType, double balance) {
        this.userId = userId;
        this.cryptoType = cryptoType;
        this.balance = balance;
    }
}
