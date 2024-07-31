package com.crypto.web;

import com.crypto.entity.Price;
import com.crypto.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/latest")
    public ResponseEntity<List<Price>> getLatestBestAggregatedPrice() {
        List<Price> response = priceService.getLatestBestAggregatedPrice();
        return ResponseEntity.ok(response);
    }
}
