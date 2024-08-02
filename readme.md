# Crypto Trading System

---
## Description
Develop a crypto trading system using the Spring Boot framework with an in-memory H2 Database. The system should support basic crypto trading functionalities.

## Functional Scope
1. **Trade Execution**: Users can buy/sell the supported crypto trading pairs.
2. **Transaction History**: Users can view a list of their trading transactions.
3. **Wallet Balance**: Users can view their cryptocurrency wallet balance.

## Assumptions
1. Users are already authenticated and authorized to access the APIs.
2. Each user has an initial wallet balance of 50,000 USDT in the database.
3. The system supports only Ethereum (ETH-USDT) and Bitcoin (BTC-USDT) trading pairs.

## Tasks

### 1. Price Aggregation
- Aggregate prices from the following sources:
    - **Binance**: [Binance API](https://api.binance.com/api/v3/ticker/bookTicker)
    - **Huobi**: [Huobi API](https://api.huobi.pro/market/tickers)
- Implement a scheduler to retrieve the prices every 10 seconds and store the best prices in the database.
    - Use the bid price for SELL orders.
    - Use the ask price for BUY orders.

### 2. Retrieve Latest Best Aggregated Price
- Create an API endpoint to retrieve the latest best aggregated price.

### 3. Execute Trades
- Create an API endpoint allowing users to trade based on the latest best aggregated price.

### 4. Retrieve Wallet Balance
- Create an API endpoint to retrieve the user's cryptocurrency wallet balance.

### 5. Retrieve Trading History
- Create an API endpoint to retrieve the user's trading history.

## Important Notes
- Use git to track your progress throughout the development.
    - Perform an "initial commit" at the start.
    - Perform an "end commit" at the end.
- You can either zip your entire project or provide a link to your GitHub repository for review.

---
## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- Git

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

### API Documentation
The API documentation will be available at `/swagger-ui.html` after running the application.

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
        "type": "BUY/SELL",
        "pair": "ETH-USDT/BTC-USDT",
        "amount": "<amount>"
      }
      ```

### Get Trading History
- **GET** `/api/v1/trades/{userId}/history`
    - Retrieves the user's trading history.

## Contribution Guidelines
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.
