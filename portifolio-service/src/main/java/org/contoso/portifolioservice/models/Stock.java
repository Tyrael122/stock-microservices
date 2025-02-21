package org.contoso.portifolioservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    private String symbol;
    private String name;
    private int quantity;

    public Stock(UUID userId, String symbol, String name, int quantity) {
        this.userId = userId;
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
    }
}
