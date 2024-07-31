package com.crypto.service;

import com.crypto.data.WalletRepository;
import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import com.crypto.exception.InsufficientBalanceException;
import com.crypto.exception.WalletNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet(1001L, CryptoType.USDT, 100.0);
    }

    @Test
    void getWalletsByUserId_shouldGetListOfWallets() {
        when(walletRepository.findByUserId(1001L)).thenReturn(Optional.of(List.of(wallet)));

        List<Wallet> wallets = walletService.getWalletsByUserId(1001L);

        assertNotNull(wallets);
        assertEquals(1, wallets.size());
        assertEquals(100.0, wallets.get(0).getBalance());
    }

    @Test
    void getWalletsByUserId_WalletNotFound() {
        when(walletRepository.findByUserId(1001L)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.getWalletsByUserId(1001L));
    }

    @Test
    void getWalletByUserIdAndCryptoType_shouldReturnSingleWallet() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.of(wallet));

        Wallet foundWallet = walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.USDT);

        assertNotNull(foundWallet);
        assertEquals(100.0, foundWallet.getBalance());
    }

    @Test
    void getWalletByUserIdAndCryptoType_WalletNotFound() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.getWalletByUserIdAndCryptoType(1001L, CryptoType.USDT));
    }

    @Test
    void updateWalletBalance_AddFunds() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.of(wallet));
        walletService.updateWalletBalance(1001L, CryptoType.USDT, 50.0);

        verify(walletRepository, times(1)).save(wallet);
        assertEquals(150.0, wallet.getBalance());
    }

    @Test
    void updateWalletBalance_DeductFunds() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.of(wallet));
        walletService.updateWalletBalance(1001L, CryptoType.USDT, -50.0);

        verify(walletRepository, times(1)).save(wallet);
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void updateWalletBalance_InsufficientFunds() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.USDT)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientBalanceException.class, () -> walletService.updateWalletBalance(1001L, CryptoType.USDT, -150.0));
    }

    @Test
    void updateWalletBalance_CreateNewWallet() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.ETH)).thenReturn(Optional.empty());

        walletService.updateWalletBalance(1001L, CryptoType.ETH, 50.0);

        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void updateWalletBalance_CreateNewWalletWithNegativeBalance() {
        when(walletRepository.findByUserIdAndCryptoType(1001L, CryptoType.ETH)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.updateWalletBalance(1001L, CryptoType.ETH, -50.0));
    }
}