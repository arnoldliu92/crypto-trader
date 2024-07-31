package com.crypto.util;

import com.crypto.entity.Price;
import com.crypto.enums.CryptoType;
import com.crypto.enums.CryptoWebsite;
import com.crypto.enums.DataSource;
import com.crypto.response.CryptoTickerResponse;
import com.crypto.response.CryptoNestedTickerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class PriceAggregatorUtil {
    private final Logger logger = LoggerFactory.getLogger(PriceAggregatorUtil.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // There are 2 types of Responses from the Crypto websites, kept in array for scalability
    // eg. NonNested - BINANCE: [{...}, {...}]
    // eg. Nested - HUOBI: {"data":[{...}, {...}]}
    private final Map<String, CryptoWebsite[]> websiteMapping = Map.of(
        "Non Nested", new CryptoWebsite[] { CryptoWebsite.BINANCE },
        "Nested", new CryptoWebsite[] { CryptoWebsite.HUOBI }
    );

    public List<Price[]> getBestPricesList() {
        List<Price[]> bestPrices = new ArrayList<>();

        List<Pair<String, CryptoTickerResponse[]>> fullListOfCryptoTicker = fetchTickerPrices();
        for (CryptoType cryptoType : CryptoType.values()) {
            bestPrices.add(getBestPriceDetails(fullListOfCryptoTicker, cryptoType));
        }

        return bestPrices;
    }

    private List<Pair<String, CryptoTickerResponse[]>> fetchTickerPrices() {
        List<Pair<String, CryptoTickerResponse[]>> fullListOfCryptoTicker = new ArrayList<>();
        CryptoWebsite[] nonNestedResponses = websiteMapping.get("Non Nested");
        CryptoWebsite[] nestedResponses = websiteMapping.get("Nested");

        // The results from NonNested/Nested will be treated differently
        // Pair used to retain the Source info (Binance, Huobi, etc)
        for (CryptoWebsite nonNestedResponse : nonNestedResponses) {
            String response = restTemplate.getForObject(nonNestedResponse.getApiUrl(), String.class);
            try {
                fullListOfCryptoTicker.add(Pair.of(nonNestedResponse.toString(), objectMapper.readValue(response, CryptoTickerResponse[].class)));
            } catch (IOException exception) {
                throw new RuntimeException("Failed to parse Binance response", exception);
            }
        }
        for (CryptoWebsite nestedResponse : nestedResponses) {
            String response = restTemplate.getForObject(nestedResponse.getApiUrl(), String.class);
            try {
                CryptoNestedTickerResponse cryptoNestedTickerResponse = objectMapper.readValue(response, CryptoNestedTickerResponse.class);
                fullListOfCryptoTicker.add(Pair.of(nestedResponse.toString(), cryptoNestedTickerResponse.getCryptoTickerResponses()));
            } catch (IOException exception) {
                throw new RuntimeException("Failed to parse Huobi response", exception);
            }
        }

        return fullListOfCryptoTicker;
    }

    /**
     * Iterate through the full list of Cryto Ticker response gotten from all the cryto source websites
     *
     * @param cryptoType Type of crypto interested in finding the best bid or ask price
     * @param fullList The full list of Cryto Ticker response gotten from all the cryto source websites,
     *                 the pair also stores the source website as the first element
     * @return Price[] { bestBidPrice, bestAskPrice }
     */
    private Price[] getBestPriceDetails(List<Pair<String, CryptoTickerResponse[]>> fullList, CryptoType cryptoType) {
        Price bestBidPrice = new Price();
        Price bestAskPrice = new Price();
        for (Pair<String, CryptoTickerResponse[]> tickers : fullList) {
            for (CryptoTickerResponse ticker : tickers.getSecond()) {
                if (ticker.getSymbol().equals(cryptoType)) {
                    Price[] comparePair = setPriceDetails(ticker, bestBidPrice, bestAskPrice, tickers.getFirst());
                    bestBidPrice = comparePair[0];
                    bestAskPrice = comparePair[1];
                }
            }
        }
        bestBidPrice.setTimestampCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        bestAskPrice.setTimestampCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        return new Price[] { bestBidPrice, bestAskPrice };
    }



    /**
     * Compare new bid and ask price with existing details in Price object
     * If newBidPrice < existingBidPrice -> Replace with new
     * If newAskPrice > existingAskPrice -> Replace with new
     *
     * @param ticker Individual ticker information
     * @param bestBidPrice Price object used to store the eventual best Bid price
     * @param bestAskPrice Price object used to store the eventual best Ask price
     * @param source Website source for which ticker is gotten from
     * @return Price[] { bestBidPrice, bestAskPrice }
     */
    private Price[] setPriceDetails(CryptoTickerResponse ticker, Price bestBidPrice, Price bestAskPrice, String source) {
        if (bestBidPrice.getCryptoType() == null) {
            bestBidPrice.setDataSource(DataSource.valueOf(source));
            bestBidPrice.setCryptoType(ticker.getSymbol());
            bestBidPrice.setBidPrice(ticker.getBidPrice());
            bestBidPrice.setAskPrice(ticker.getAskPrice());
        } else if (bestBidPrice.getBidPrice() < ticker.getBidPrice()) {
            bestBidPrice.setDataSource(DataSource.valueOf(source));
            bestBidPrice.setBidPrice(ticker.getBidPrice());
        }
        if (bestAskPrice.getCryptoType() == null) {
            bestAskPrice.setDataSource(DataSource.valueOf(source));
            bestAskPrice.setCryptoType(ticker.getSymbol());
            bestAskPrice.setBidPrice(ticker.getBidPrice());
            bestAskPrice.setAskPrice(ticker.getAskPrice());
        } else if (bestAskPrice.getAskPrice() > ticker.getAskPrice()) {
            bestBidPrice.setDataSource(DataSource.valueOf(source));
            bestBidPrice.setAskPrice(ticker.getAskPrice());
        }
        return new Price[] { bestBidPrice, bestAskPrice };
    }

}
