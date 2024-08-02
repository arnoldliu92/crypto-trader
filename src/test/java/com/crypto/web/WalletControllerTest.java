package com.crypto.web;

import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import com.crypto.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {
    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @Test
    void getWalletsByUserId_shouldReturnListOfWalletsOwnedByUser() {
        Wallet expectedWallet = new Wallet(1001L, CryptoType.USDT, 100.0);
        List<Wallet> expectedWalletList = List.of(expectedWallet);
        when(walletService.getWalletsByUserId(anyLong())).thenReturn(expectedWalletList);

        ResponseEntity<List<Wallet>> response = walletController.getWalletsByUserId(1001L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWalletList, response.getBody());
        verify(walletService, times(1)).getWalletsByUserId(1001L);
    }
}