package org.contoso.portfolioservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {
    private String symbol;   // e.g. "AAPL" for Apple
    private String name;     // e.g. "Apple Inc."
    private int quantity;    // Number of shares
}