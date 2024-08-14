package com.crypto.web;

import com.crypto.dto.TradeRequest;
import com.crypto.entity.Trade;
import com.crypto.enums.CryptoType;
import com.crypto.enums.TradeType;
import com.crypto.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade(1001L, TradeType.BUY, CryptoType.BTCUSDT, BigDecimal.valueOf(50000.0), BigDecimal.ONE, null);
    }

    @Test
    void getTradingHistory_shouldReturnListOfTrades() {
        when(tradeService.getTradingHistory(anyLong())).thenReturn(List.of(trade));

        ResponseEntity<List<Trade>> response = tradeController.getTradingHistory(1001L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(trade, response.getBody().get(0));
        verify(tradeService, times(1)).getTradingHistory(1001L);
    }

    @Test
    void executeTrade_purchaseOperationDoneOnce() {
        when(tradeService.filterTrade(anyLong(), any(TradeRequest.class))).thenReturn(trade);

        TradeRequest actualTradeRequest = new TradeRequest(TradeType.BUY, CryptoType.BTCUSDT, BigDecimal.ONE);
        ResponseEntity<Trade> response = tradeController.executeTrade(1001L, actualTradeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trade, response.getBody());
        verify(tradeService, times(1)).filterTrade(1001L, actualTradeRequest);
    }

    @Test
    void executeTrade_sellOperationDoneOnce() {
        trade.setTradeType(TradeType.SELL);
        when(tradeService.filterTrade(anyLong(), any(TradeRequest.class))).thenReturn(trade);

        TradeRequest actualTradeRequest = new TradeRequest(TradeType.SELL, CryptoType.BTCUSDT, BigDecimal.ONE);
        ResponseEntity<Trade> response = tradeController.executeTrade(1001L, actualTradeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trade, response.getBody());
        verify(tradeService, times(1)).filterTrade(1001L, actualTradeRequest);
    }
}