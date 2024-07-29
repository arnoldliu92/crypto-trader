package com.crypto.data;

import com.crypto.entity.Trade;
import com.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
