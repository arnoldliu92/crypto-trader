package com.crypto.service;

import com.crypto.data.WalletRepository;
import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import com.crypto.exception.InsufficientBalanceException;
import com.crypto.exception.WalletNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    private WalletRepository walletRepository;

    /**
     * Retrieves the wallet balance for a specific user.
     *
     * @param   userId the ID of the user
     * @return  a list of Wallet objects for the user
     */
    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository
                .findByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);
    }

    /**
     * Find the wallet balance for a specific user and cryptocurrency.
     *
     * @param userId        the ID of the user
     * @param cryptoType    the type of currency (e.g. USDT, ETH, BTC)
     * @return              Wallet object
     */
    public Wallet getWalletByUserIdAndCryptoType(Long userId, CryptoType cryptoType) {
        return walletRepository.findByUserIdAndCryptoType(userId, cryptoType)
                .orElseThrow(() -> new WalletNotFoundException(userId, cryptoType));
    }

    /**
     * Updates the wallet balance for a specific user and cryptocurrency.
     * If wallet does not exist and we want to add currency -> create new Wallet object and save
     * If wallet does not exist and we want to deduct currency -> throw exception
     * If wallet exist but has less balance than the amount required -> throw exception
     *
     * @param userId     the ID of the user
     * @param cryptoType the type of currency (e.g. USDT, ETH, BTC)
     * @param amount     the amount to update the wallet by
     */
    @Transactional
    public void updateWalletBalance(Long userId, CryptoType cryptoType, Double amount) {
        logger.debug("Updating {} user {} account by {} amount...", userId, cryptoType, amount);

        Wallet wallet = walletRepository.findByUserIdAndCryptoType(userId, cryptoType)
                .orElseGet(() -> {
                    if (amount > 0) {
                        return new Wallet(userId, cryptoType, 0.0);
                    }
                    throw new WalletNotFoundException(userId, cryptoType);
                });

        double newBalance = wallet.getBalance() + amount;
        if (newBalance < 0) {
            throw new InsufficientBalanceException(userId, CryptoType.USDT, wallet.getBalance(), amount);
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }
}