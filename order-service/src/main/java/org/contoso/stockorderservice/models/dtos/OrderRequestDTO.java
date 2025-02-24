package org.contoso.stockorderservice.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.contoso.stockorderservice.models.Order;
import org.contoso.stockorderservice.models.OrderType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private UUID userId;
    private String stockSymbol;
    private int quantity;
    private OrderType type;

    public Order toOrder() {
        Order order = new Order();
        order.setUserId(userId);
        order.setStockSymbol(stockSymbol);
        order.setQuantity(quantity);
        order.setType(type);
        return order;
    }
}
