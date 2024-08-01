package com.crypto.service;

import com.crypto.data.PriceRepository;
import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import com.crypto.exception.PriceNotFoundException;
import com.crypto.util.PriceAggregatorUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void getLatestPrice_shouldReturnPrice_whenPriceExists() {
        CryptoType cryptoType = CryptoType.BTCUSDT;
        Price expectedPrice = new Price(cryptoType, 100.0, 110.0);
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest)).thenReturn(List.of(expectedPrice));
        Price result = priceService.getLatestPrice(cryptoType);

        assertEquals(expectedPrice, result);
    }

    @Test
    void getLatestPrice_shouldThrowException_whenPriceNotFound() {
        CryptoType cryptoType = CryptoType.ETHUSDT;
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest)).thenReturn(new ArrayList<>());

        assertThrows(PriceNotFoundException.class, () -> priceService.getLatestPrice(cryptoType));
    }

    @Test
    void getLatestBestAggregatedPrice_shouldReturnListOfPrices() {
        Price btcPrice = new Price(CryptoType.BTCUSDT, 100.0, 110.0);
        Price ethPrice = new Price(CryptoType.ETHUSDT, 300.0, 310.0);
        PageRequest pageRequest = PageRequest.of(0, 1);
        for (CryptoType cryptoType : CryptoType.values()) {
            if (cryptoType == CryptoType.BTCUSDT) {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest)).thenReturn(List.of(btcPrice));
            } else if (cryptoType == CryptoType.ETHUSDT) {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest)).thenReturn(List.of(ethPrice));
            } else {
                when(priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest)).thenReturn(new ArrayList<>());
            }
        }

        List<Price> result = priceService.getLatestBestAggregatedPrice();

        assertEquals(2, result.size());
        assertTrue(result.contains(btcPrice));
        assertTrue(result.contains(ethPrice));
    }

    @Test
    void updatePrices_shouldSavePricesAndUpdateCache() {
        Price[] btcPrices = { new Price(CryptoType.BTCUSDT, 100.0, 110.0) };
        Price[] ethPrices = { new Price(CryptoType.ETHUSDT, 300.0, 310.0) };
        List<Price[]> bestPricesList = Arrays.asList(btcPrices, ethPrices);
        when(priceAggregatorUtil.getBestPricesList()).thenReturn(bestPricesList);

        priceService.updatePrices();

        verify(priceRepository, times(2)).save(any(Price.class));
        verify(priceAggregatorUtil, times(1)).getBestPricesList();
    }
}