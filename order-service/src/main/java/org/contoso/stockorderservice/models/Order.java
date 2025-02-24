package org.contoso.stockorderservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String stockSymbol;
    private int quantity;
    private BigDecimal price;
    private OrderType type;
    private OrderStatus status; // "PENDING", "COMPLETED", "FAILED"
}
