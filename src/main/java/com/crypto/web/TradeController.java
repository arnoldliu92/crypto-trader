package com.crypto.web;

import com.crypto.dto.TradeRequest;
import com.crypto.entity.Trade;
import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import com.crypto.exception.InsufficientBalanceException;
import com.crypto.exception.InvalidInputException;
import com.crypto.exception.WalletNotFoundException;
import com.crypto.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    /**
     * To retrieve all trade histories of the given user ID
     *
     * @param   userId  User ID
     * @return          List of Trade objects
     */
    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Trade>> getTradingHistory(@PathVariable Long userId) {
        // TODO: Pagination or limit the number of records/history retrieved
        logger.info("Retrieving trade history for {}", userId);
        List<Trade> history = tradeService.getTradingHistory(userId);
        return ResponseEntity.ok(history);
    }

    /**
     *
     * @param userId        User ID
     * @param tradeRequest  DTO for TradeType, CryptoType, amount
     * @return              Trade object
     */
    @PostMapping
    public ResponseEntity<Trade> executeTrade (
            @RequestHeader Long userId,
            @RequestBody TradeRequest tradeRequest
        ) throws WalletNotFoundException, InsufficientBalanceException, InvalidInputException {
        logger.info("Initiating {} trade for {} using {} by {}", tradeRequest.getTradeType(), userId, tradeRequest.getCryptoType(), tradeRequest.getAmount());
        Trade trade = tradeService.filterTrade(userId, tradeRequest);
        return ResponseEntity.ok(trade);
    }
}
