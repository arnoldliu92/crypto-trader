package com.crypto.service;

import com.crypto.data.TradeRepository;
import com.crypto.entity.Price;
import com.crypto.entity.Trade;
import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import com.crypto.exception.InsufficientBalanceException;
import com.crypto.util.SqlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private SqlUtil sqlUtil;

    private Trade trade;
    private Wallet wallet;
    private Price price;

    @BeforeEach
    void setUp() {
        trade = new Trade(1001L, TradeType.BUY, CryptoType.BTC, 50000.0, 1.0, sqlUtil.createCurrentTimestamp());
        wallet = new Wallet(1001L, CryptoType.USDT, 100000.0);
        price = new Price(CryptoType.BTC, 50000.0, 51000.0);
    }

    @Test
    void getTradingHistory_shouldReturnListOfTradeHistories() {
        when(tradeRepository.findByUserId(1001L)).thenReturn(List.of(trade));

        List<Trade> history = tradeService.getTradingHistory(1001L);

        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(trade, history.get(0));
    }

    /**
     * Work in progress
    @Test
    void purchaseCrypto_shouldExecuteTradeAndUpdateWalletBalance() {
        when(priceService.getLatestPrice(CryptoType.BTC)).thenReturn(price);
        when(walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(wallet);
        doNothing().when(walletService).updateWalletBalance(anyLong(), any(CryptoType.class), anyDouble());
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade executedTrade = tradeService.purchaseCrypto(1001L, CryptoType.BTC, 1.0);

        assertNotNull(executedTrade);
        assertEquals(TradeType.BUY, executedTrade.getTradeType());
        assertEquals(CryptoType.BTC, executedTrade.getCryptoType());
        assertEquals(1.0, executedTrade.getAmount());

        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.USDT, -50000.0);
        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.BTC, 1.0);
    }
     */

//
//    @Test
//    void sellCrypto_shouldExecuteTradeAndUpdateWalletBalance() {
//        Trade sellTrade = new Trade(1L, TradeType.SELL, CryptoType.BTC, 50000.0, 1.0, sqlUtil.createCurrentTimestamp());
//        Wallet sellWallet = new Wallet(1L, CryptoType.BTC, 1.0);
//
//        when(priceService.getLatestPrice(CryptoType.BTC)).thenReturn(price);
//        when(walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.BTC)).thenReturn(sellWallet);
//        doNothing().when(walletService).updateWalletBalance(anyLong(), any(CryptoType.class), anyDouble());
//        when(tradeRepository.save(any(Trade.class))).thenReturn(sellTrade);
//
//        Trade executedTrade = tradeService.sellCrypto(1001L, CryptoType.BTC, 1.0);
//
//        assertNotNull(executedTrade);
//        assertEquals(TradeType.SELL, executedTrade.getTradeType());
//        assertEquals(CryptoType.BTC, executedTrade.getCryptoType());
//        assertEquals(1.0, executedTrade.getAmount());
//
//        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.BTC, -1.0);
//        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.USDT, 50000.0);
//    }
//
//    @Test
//    void testPurchaseCryptoInsufficientFunds() {
//        when(priceService.getLatestPrice(CryptoType.BTC)).thenReturn(price);
//        when(walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(new Wallet(1001L, CryptoType.USDT, 1000.0));
//
//        assertThrows(InsufficientBalanceException.class, () -> tradeService.purchaseCrypto(1001L, CryptoType.BTC, 1.0));
//    }
//
//    @Test
//    void testSellCryptoInsufficientFunds() {
//        when(priceService.getLatestPrice(CryptoType.BTC)).thenReturn(price);
//        when(walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.BTC)).thenReturn(new Wallet(1001L, CryptoType.BTC, 0.5));
//
//        assertThrows(InsufficientBalanceException.class, () -> tradeService.sellCrypto(1001L, CryptoType.BTC, 1.0));
//    }
}