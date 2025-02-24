package org.contoso.stockorderservice.services.dtos;

import lombok.Data;

@Data
public class StockDTO {
    private String symbol;   // e.g. "AAPL" for Apple
    private String name;     // e.g. "Apple Inc."
    private int quantity;    // Number of shares
}