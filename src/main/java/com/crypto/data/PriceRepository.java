package com.crypto.data;

import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE p.crypto_type = :cryptoType ORDER BY p.timestamp_created DESC LIMIT 1")
    Optional<Price> findLatestPriceByCryptoType(@Param("cryptoType") CryptoType cryptoType);
}
