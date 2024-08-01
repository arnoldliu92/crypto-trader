package com.crypto.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param tradeType     To determine if it is a BUY or SELL operation
     * @param cryptoType    To determine the type of currency involved
     * @param amount        To determine the amount debited or credited
     * @return              Trade object
     */
    @PostMapping
    public ResponseEntity<Trade> executeTrade(
            @RequestParam Long userId,
            @RequestParam TradeType tradeType,
            @RequestParam CryptoType cryptoType,
            @RequestParam double amount) {
        logger.info("Initiating {} trade for {} using {} by {}", tradeType, userId, cryptoType, amount);
        try {
            Trade trade;
            if (TradeType.BUY.equals(tradeType)) {
                trade = tradeService.purchaseCrypto(userId, cryptoType, amount);
            } else if (TradeType.SELL.equals(tradeType)) {
                trade = tradeService.sellCrypto(userId, cryptoType, amount);
            } else {
                throw new InvalidInputException(tradeType);
            }
            return ResponseEntity.ok(trade);
        } catch (WalletNotFoundException | InsufficientBalanceException exception) {
            throw exception;
        }
    }
}
