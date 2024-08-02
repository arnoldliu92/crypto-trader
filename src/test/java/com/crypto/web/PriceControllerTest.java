package com.crypto.web;

import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import com.crypto.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @Test
    void getLatestBestAggregatedPrice_shouldReturnListOfPrices() {
        Price btcPrice = new Price(CryptoType.BTC, 50000.0, 50100.0);
        Price ethPrice = new Price(CryptoType.ETH, 3000.0, 3010.0);
        List<Price> expectedPrices = Arrays.asList(btcPrice, ethPrice);

        when(priceService.getLatestBestAggregatedPrice()).thenReturn(expectedPrices);

        ResponseEntity<List<Price>> response = priceController.getLatestBestAggregatedPrice();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPrices, response.getBody());
        verify(priceService, times(1)).getLatestBestAggregatedPrice();
    }
}