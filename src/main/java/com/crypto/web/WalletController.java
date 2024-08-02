package com.crypto.web;

import com.crypto.entity.Wallet;
import com.crypto.exception.WalletNotFoundException;
import com.crypto.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    @Autowired
    private WalletService walletService;

    /**
     * Retrieves the wallet balance for a specific user.
     *
     * @param   userId the ID of the user
     * @return  a list of Wallet objects for the user
     */
    @GetMapping("/{userId}/wallets")
    public ResponseEntity<List<Wallet>> getWalletsByUserId(@PathVariable Long userId) throws WalletNotFoundException {
        logger.info("Getting wallets for {}...", userId);
        List<Wallet> wallets = walletService.getWalletsByUserId(userId);
        return ResponseEntity.ok(wallets);
    }

    /**
     * Updates the wallet balance for a specific user and cryptocurrency.
     *
     * @param userId the ID of the user
     * @param cryptoType the type of cryptocurrency (e.g., ETH, BTC)
     * @param amount the amount to update the wallet by
     */
    /**
     * Future dev requirement
     * @PostMapping("/{userId}/wallet/balance")
     * public ResponseEntity<String> updateWalletBalance(
     *         @PathVariable Long userId,
     *         @RequestParam String cryptoType,
     *         @RequestParam double amount) {
     *     logger.debug("Updating wallet for {} of type {} by amount {}", userId, cryptoType, amount);
     *     walletService.updateWalletBalance(userId, CryptoType.valueOf(cryptoType), amount);
     *     return ResponseEntity.ok("Wallet balance updated successfully");
     * }
    */
}