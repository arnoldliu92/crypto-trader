package com.crypto.data;

import com.crypto.entity.Price;
import com.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findTopByOrderByTimestampDesc();
}
