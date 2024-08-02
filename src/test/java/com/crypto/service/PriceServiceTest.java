package com.crypto.service;

import com.crypto.data.PriceRepository;
import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import com.crypto.exception.PriceNotFoundException;
import com.crypto.util.PriceAggregatorUtil;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceAggregatorUtil priceAggregatorUtil;

    @InjectMocks
    private PriceService priceService;

    private Price btcPrice;
    private Price ethPrice;

    @BeforeEach
    void setUp() {
        btcPrice = new Price(CryptoType.BTC, 100.0, 110.0);
        ethPrice = new Price(CryptoType.ETH, 300.0, 310.0);
    }

    @Test
    void getLatestPrice_shouldReturnPrice_whenPriceExists() {
        CryptoType cryptoType = CryptoType.BTC;
        Price expectedPrice = btcPrice;
        when(priceRepository.findLatestPriceByCryptoType(cryptoType)).thenReturn(Optional.of(expectedPrice));

        Price result = priceService.getLatestPrice(cryptoType);

        assertEquals(expectedPrice, result);
    }

    @Test
    void getLatestPrice_shouldThrowException_whenPriceNotFound() {
        CryptoType cryptoType = CryptoType.ETH;
        when(priceRepository.findLatestPriceByCryptoType(cryptoType)).thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> priceService.getLatestPrice(cryptoType));
    }

    @Test
    void getLatestBestAggregatedPrice_shouldReturnListOfPrices() {
        for (CryptoType cryptoType : CryptoType.values()) {
            if (cryptoType == CryptoType.BTC) {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType)).thenReturn(Optional.of(btcPrice));
            } else if (cryptoType == CryptoType.ETH) {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType)).thenReturn(Optional.of(ethPrice));
            } else {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType)).thenReturn(Optional.empty());
            }
        }

        List<Price> result = priceService.getLatestBestAggregatedPrice();

        assertEquals(2, result.size());
        assertTrue(result.contains(btcPrice));
        assertTrue(result.contains(ethPrice));
    }

    @Test
    void updatePrices_shouldSavePricesAndUpdateCache() {
        Price[] btcPrices = { btcPrice };
        Price[] ethPrices = { ethPrice };
        List<Price[]> bestPricesList = Arrays.asList(btcPrices, ethPrices);
        when(priceAggregatorUtil.getBestPricesList()).thenReturn(bestPricesList);

        priceService.updatePrices();

        verify(priceRepository, times(2)).save(any(Price.class));
        verify(priceAggregatorUtil, times(1)).getBestPricesList();
    }
}