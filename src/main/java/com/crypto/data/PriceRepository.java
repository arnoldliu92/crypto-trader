package com.crypto.data;

import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE p.cryptoType = :cryptoType ORDER BY p.timestampCreated DESC")
    List<Price> findLatestPriceByCryptoType(@Param("cryptoType") CryptoType cryptoType, Pageable pageable);
}
