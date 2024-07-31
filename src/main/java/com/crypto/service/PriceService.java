package com.crypto.service;

import com.crypto.data.PriceRepository;
import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import com.crypto.exception.PriceNotFoundException;
import com.crypto.util.PriceAggregatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceAggregatorUtil priceAggregatorUtil;

    public Price getLatestPrice(CryptoType cryptoType) {
        return priceRepository.findLatestPriceByCryptoType(cryptoType)
                .orElseThrow(() -> new PriceNotFoundException(cryptoType));
    }

    @Cacheable("latestBestPrice")
    public List<Price> getLatestBestAggregatedPrice() {
        List<Price> bestPricesList = new ArrayList<>();
        CryptoType[] cryptoTypes = CryptoType.values();
        for (CryptoType cryptoType : cryptoTypes) {
            try {
                Optional<Price> latestBestPrice = priceRepository.findLatestPriceByCryptoType(cryptoType);
                latestBestPrice.ifPresent(bestPricesList::add);
            } catch (PriceNotFoundException exception) {
                logger.error(new PriceNotFoundException(cryptoType).getMessage());
            }
        }
        return bestPricesList;
    }

    /**
     * Fetch prices from external sources
     * Update the aggregated best prices to DB
     * Cache and update DB every 10 seconds
     */
    @CacheEvict(value = "latestBestPrice", allEntries = true)
    public void updatePrices() {
        // Fetch prices from external sources
        logger.info("Fetching and aggregating prices");
        List<Price[]> bestPricesList = priceAggregatorUtil.getBestPricesList();

        for (Price[] entryByCrypto : bestPricesList) {
            for (Price priceDetail : entryByCrypto) {
                priceRepository.save(priceDetail);
                // Evict and update individual cache entries for each crypto type
                CryptoType cryptoType = priceDetail.getCryptoType();
                updateLatestPriceCache(cryptoType);
            }
        }
    }

    @CacheEvict(value = "latestPrice", key = "#cryptoType")
    private void updateLatestPriceCache(CryptoType cryptoType) {
        // This method is used to evict the cache entry for a specific crypto type
        logger.info("Evicting cache for crypto type: " + cryptoType);
    }
}
