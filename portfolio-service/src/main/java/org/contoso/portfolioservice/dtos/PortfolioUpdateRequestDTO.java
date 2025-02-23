package org.contoso.portfolioservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PortfolioUpdateRequestDTO {

    private String symbol;   // Stock symbol, e.g., "AAPL"
    private int quantity;    // Quantity to be updated (can be positive for purchase, negative for sale)
    private BigDecimal price;    // Price of stock at the time of update
    private PortfolioTransactionType transactionType; // Either "BUY" or "SELL"
}
