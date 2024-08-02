package com.crypto.config;

import com.crypto.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    private PriceService priceService;

    @Scheduled(fixedRate = 10000)
    public void fetchAndUpdatePrices() {
        priceService.updatePrices();
    }
}
