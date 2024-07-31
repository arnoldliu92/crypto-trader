package com.crypto.web;

import com.crypto.entity.Price;
import com.crypto.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price")
public class PriceController {
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceService priceService;

    /**
     * To retrieve the latest Bid and Ask price from DB
     * @return List of Price objects of ALL crypto types interested
     */
    @GetMapping("/latest")
    public ResponseEntity<List<Price>> getLatestBestAggregatedPrice() {
        logger.info("Getting latest best aggretated price...");
        List<Price> response = priceService.getLatestBestAggregatedPrice();
        return ResponseEntity.ok(response);
    }
}
