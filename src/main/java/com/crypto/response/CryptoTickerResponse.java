package com.crypto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"high", "open", "count", "vol", "low", "bidQty", "askSize", "askQty", "bidSize", "close", "amount"})
public class CryptoTickerResponse {
    private String symbol;

    @JsonProperty(value = "bidPrice", required = false)
    private Double bidPrice;

    @JsonProperty(value = "askPrice", required = false)
    private Double askPrice;

    @JsonProperty(value = "bid", required = false)
    public void setBidFromBid(Double bid) {
        if (this.bidPrice == null) {
            this.bidPrice = bid;
        }
    }

    @JsonProperty(value = "ask", required = false)
    public void setAskFromAsk(Double ask) {
        if (this.askPrice == null) {
            this.askPrice = ask;
        }
    }
}
