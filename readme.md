## Getting Started

### Prerequisites
- Java 17 or higher
- Maven

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd crypto-trader-system
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

## Table Structure

### User
| Column      | Type   | Description            |
|-------------|--------|------------------------|
| user_id     | Long   | Primary key            |
| full_name   | String | User's Full Name       |
| email       | String | Email Address          |


### Wallet
| Column      | Type   | Description            |
|-------------|--------|------------------------|
| wallet_id   | Long   | Primary key            |
| user_id     | Long   | Foreign key to Users   |
| crypto_type | String | USDT/ETH-USDT/BTC-USDT |
| balance     | Double | Wallet balance         |

### Trade
| Column            | Type      | Description           |
|-------------------|-----------|-----------------------|
| trade_id          | Long      | Primary key           |
| user_id           | Long      | Foreign key to Users  |
| trade_type        | String    | Trade type (BUY/SELL) |
| crypto_type       | String    | USDT/ETH/BTC          |
| price             | Double    | Trade price           |
| amount            | Double    | Amount traded         |
| timestamp_created | Timestamp | Trade timestamp       |

### Price
| Column            | Type      | Description              |
|-------------------|-----------|--------------------------|
| price_id          | Long      | Primary key              |
| source            | String    | Data source (Website)    |
| crypto_type       | String    | USDT/ETH/BTC             |
| bid_price         | Double    | Current bid price (SELL) |
| ask_price         | Double    | Current ask price (BUY)  |
| timestamp_created | Timestamp | Timestamp of the price   |

## Scheduler
Implement a scheduler to fetch and update prices from Binance and Huobi every 10 seconds. Store the best prices (bid for sell orders, ask for buy orders) in the `Prices` table.

## API Endpoints

### Get The Latest Best Price
- **GET** `/api/v1/price/latest`
    - Retrieves the latest best aggregated price.

### Get Wallet Balance
- **GET** `/api/v1/users/{userId}/wallets`
    - Retrieves the user's cryptocurrency wallet balance.

### Trade
- **POST** `/api/v1/trades`
    - Allows users to trade based on the latest best aggregated price.
    - Request Body:
      ```json
      {
        "tradeType": "BUY/SELL",
        "cryptoType": "ETHUSDT/BTCUSDT",
        "amount": "<amount>"
      }
      ```

### Get Trading History
- **GET** `/api/v1/trades/{userId}/history`
    - Retrieves the user's trading history.

### Get User
- **GET** `/api/v1/users`
    - Retrieves the user's user_id.

## Contribution Guidelines
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.
