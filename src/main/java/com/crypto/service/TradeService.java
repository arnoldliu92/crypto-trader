package com.crypto.service;

import com.crypto.data.TradeRepository;
import com.crypto.entity.Price;
import com.crypto.entity.Trade;
import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import com.crypto.exception.InsufficientBalanceException;
import com.crypto.exception.WalletNotFoundException;
import com.crypto.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private final SqlUtil sqlUtil = new SqlUtil();

    public List<Trade> getTradingHistory(Long userId) {
        return tradeRepository.findByUserId(userId);
    }

    @Transactional(rollbackFor = {WalletNotFoundException.class, InsufficientBalanceException.class})
    public Trade purchaseCrypto(Long userId, CryptoType cryptoType, double amount) {
        Price latestPrice = priceService.getLatestPrice(cryptoType);
        double totalCost = latestPrice.getAskPrice() * amount;

        // Check if user has enough USDT and deduct
        walletService.updateWalletBalance(userId, CryptoType.USDT, -totalCost);
        // Add purchased crypto to user's wallet
        walletService.updateWalletBalance(userId, cryptoType, amount);

        Trade trade = new Trade(userId, TradeType.BUY, cryptoType, latestPrice.getAskPrice(), amount, sqlUtil.createCurrentTimestamp());
        return tradeRepository.save(trade);
    }

    @Transactional(rollbackFor = {WalletNotFoundException.class, InsufficientBalanceException.class})
    public Trade sellCrypto(Long userId, CryptoType cryptoType, double amount) {
        Price latestPrice = priceService.getLatestPrice(cryptoType);
        double totalGain = latestPrice.getBidPrice() * amount;

        // Deduct sold crypto from user's wallet
        walletService.updateWalletBalance(userId, cryptoType, -amount);
        // Add gained USDT to user's wallet
        walletService.updateWalletBalance(userId, CryptoType.USDT, totalGain);

        Trade trade = new Trade(userId, TradeType.SELL, cryptoType, latestPrice.getBidPrice(), amount, sqlUtil.createCurrentTimestamp());
        return tradeRepository.save(trade);
    }
}