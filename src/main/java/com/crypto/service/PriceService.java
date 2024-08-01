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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceAggregatorUtil priceAggregatorUtil;

    public Price getLatestPrice(CryptoType cryptoType) throws PriceNotFoundException {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Price> priceList = priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest);
        if (priceList.isEmpty()) {
            logger.error("Unable to get the latest price for {}", cryptoType);
            throw new PriceNotFoundException(cryptoType);
        }
        return priceList.get(0);
    }

    @Cacheable("latestBestPrice")
    public List<Price> getLatestBestAggregatedPrice() {
        List<Price> bestPricesList = new ArrayList<>();
        CryptoType[] cryptoTypes = CryptoType.values();
        for (CryptoType cryptoType : cryptoTypes) {
            try {
                PageRequest pageRequest = PageRequest.of(0, 1);
                List<Price> latestBestPrice = priceRepository.findLatestPriceByCryptoType(cryptoType, pageRequest);
                bestPricesList.addAll(latestBestPrice);
            } catch (PriceNotFoundException exception) {
                logger.error("Failed to fetch latest best aggregated price for {}", cryptoType, exception);
            }
        }
        return bestPricesList;
    }

    /**
     * Fetch prices from external sources
     * Update the aggregated best bid and ask prices to DB
     * Cache and update DB every 10 seconds
     */
    @CacheEvict(value = "latestBestPrice", allEntries = true)
    public void updatePrices() {
        // Fetch prices from external sources
        logger.debug("Fetching aggregated best prices...");
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
        logger.debug("Evicting cache for crypto type: {}", cryptoType);
    }
}
