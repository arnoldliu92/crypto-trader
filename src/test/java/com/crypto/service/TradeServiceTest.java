package com.crypto.service;

import com.crypto.data.TradeRepository;
import com.crypto.data.WalletRepository;
import com.crypto.dto.TradeRequest;
import com.crypto.entity.Price;
import com.crypto.entity.Trade;
import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import com.crypto.util.SqlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    WalletRepository walletRepository;
    @Mock
    private WalletService walletService;
    @Mock
    private PriceService priceService;
    @Mock
    private SqlUtil sqlUtil;
    @InjectMocks
    private TradeService tradeService;

    private Price price;

    @BeforeEach
    void setUp() {
        price = new Price(CryptoType.BTCUSDT, 500.0, 510.0);
    }

    @Test
    void getTradingHistory_shouldReturnListOfTradeHistories() {
        Trade trade = new Trade(1001L, TradeType.BUY, CryptoType.BTCUSDT, 500.0, 1.0, sqlUtil.createCurrentTimestamp());
        when(tradeRepository.findByUserId(1001L)).thenReturn(List.of(trade));

        List<Trade> history = tradeService.getTradingHistory(1001L);
        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(trade, history.get(0));
    }

    @Test
    void purchaseCrypto_shouldExecuteTradeAndUpdateWalletBalance() {
        Trade trade = new Trade(1001L, TradeType.BUY, CryptoType.BTCUSDT, 500.0, 1.0, sqlUtil.createCurrentTimestamp());
        when(priceService.getLatestPrice(CryptoType.BTCUSDT)).thenReturn(price);
        doNothing().when(walletService).updateWalletBalance(1001L, CryptoType.USDT, -510.0);
        doNothing().when(walletService).updateWalletBalance(1001L, CryptoType.BTCUSDT, 1.0);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        TradeRequest tradeRequest = new TradeRequest(TradeType.BUY, CryptoType.BTCUSDT, 1.0);
        Trade executedTrade = tradeService.filterTrade(1001L, tradeRequest);
        assertNotNull(executedTrade);
        assertEquals(TradeType.BUY, executedTrade.getTradeType());
        assertEquals(CryptoType.BTCUSDT, executedTrade.getCryptoType());
        assertEquals(1.0, executedTrade.getAmount());

        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.USDT, -510.0);
        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.BTCUSDT, 1.0);
    }

    @Test
    void sellCrypto_shouldExecuteTradeAndUpdateWalletBalance() {
        Trade sellTrade = new Trade(1L, TradeType.SELL, CryptoType.BTCUSDT, 50000.0, 1.0, sqlUtil.createCurrentTimestamp());
        when(priceService.getLatestPrice(CryptoType.BTCUSDT)).thenReturn(price);
        doNothing().when(walletService).updateWalletBalance(1001L, CryptoType.BTCUSDT, -1.0);
        doNothing().when(walletService).updateWalletBalance(1001L, CryptoType.USDT, 500.0);
        when(tradeRepository.save(any(Trade.class))).thenReturn(sellTrade);

        TradeRequest tradeRequest = new TradeRequest(TradeType.SELL, CryptoType.BTCUSDT, 1.0);
        Trade executedTrade = tradeService.filterTrade(1001L, tradeRequest);
        assertNotNull(executedTrade);
        assertEquals(TradeType.SELL, executedTrade.getTradeType());
        assertEquals(CryptoType.BTCUSDT, executedTrade.getCryptoType());
        assertEquals(1.0, executedTrade.getAmount());

        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.BTCUSDT, -1.0);
        verify(walletService, times(1)).updateWalletBalance(1001L, CryptoType.USDT, 500.0);
    }

//    @Test
//    void testPurchaseCryptoInsufficientFunds() {
//        when(priceService.getLatestPrice(CryptoType.BTCUSDT)).thenReturn(price);
//        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.of(new Wallet(1001L, CryptoType.USDT, 10.0)));
//
//        assertThrows(InsufficientBalanceException.class, () -> tradeService.purchaseCrypto(1001L, CryptoType.BTCUSDT, 10000.0));
//    }
//
//    @Test
//    void testSellCryptoInsufficientFunds() {
//        when(priceService.getLatestPrice(CryptoType.BTCUSDT)).thenReturn(price);
//        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.BTCUSDT)).thenReturn(Optional.of(new Wallet(1001L, CryptoType.BTCUSDT, 1.0)));
//
//        assertThrows(InsufficientBalanceException.class, () -> tradeService.sellCrypto(1001L, CryptoType.BTCUSDT, 10.0));
//    }
}