package com.crypto;

import com.crypto.data.UserRepository;
import com.crypto.data.WalletRepository;
import com.crypto.entity.User;
import com.crypto.entity.Wallet;
import com.crypto.enums.CryptoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoadDatabase {
    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            logger.debug("Preloading... " + userRepository.save(new User("Arnold Liu", "liuyengming.tw@gmail.com")));
            Optional<User> arnold = userRepository.findIdByEmail("liuyengming.tw@gmail.com");
            logger.debug("Preset Wallet balance 50,000 USDT... " + walletRepository.save(new Wallet(arnold.get().getId(), CryptoType.USDT, 50000.0)));
            logger.debug("Preloading... " + userRepository.save(new User("Lloyd Forger", "lforger@gmail.com")));
            Optional<User> Lloyd = userRepository.findIdByEmail("lforger@gmail.com");
            logger.debug("Preset Wallet balance 50,000 USDT... " + walletRepository.save(new Wallet(arnold.get().getId(), CryptoType.USDT, 50000.0)));
        };
    }

}
