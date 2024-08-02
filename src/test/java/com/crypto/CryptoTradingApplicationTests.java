package com.crypto;

import com.crypto.web.PriceController;
import com.crypto.web.TradeController;
import com.crypto.web.UserController;
import com.crypto.web.WalletController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
class CryptoTradingApplicationTests {
	@Autowired
	PriceController priceController;
	@Autowired
	TradeController tradeController;
	@Autowired
	UserController userController;
	@Autowired
	WalletController walletController;

	@Test
	void contextLoads() throws Exception {
		assertNotNull(priceController);
		assertNotNull(tradeController);
		assertNotNull(userController);
		assertNotNull(walletController);
	}
}
