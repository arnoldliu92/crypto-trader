package com.crypto.data;

import com.crypto.entity.User;
import com.crypto.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
