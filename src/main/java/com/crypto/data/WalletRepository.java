package com.crypto.data;

import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user_id = :userId")
    Optional<List<Wallet>> findByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Wallet w WHERE w.user_id = :userId AND w.crypto_type = :cryptoType")
    Optional<Wallet> findByUserIdAndCryptoType(@Param("userId") Long userId, @Param("cryptoType") CryptoType cryptoType);

    @Query("SELECT w FROM Wallet w WHERE w.email = :email")
    Optional<Wallet> findIdByEmail(@Param("email") String email);
}
