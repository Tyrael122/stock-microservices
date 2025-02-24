package org.contoso.stockorderservice.services;

import lombok.RequiredArgsConstructor;
import org.contoso.stockorderservice.models.Order;
import org.contoso.stockorderservice.models.OrderStatus;
import org.contoso.stockorderservice.models.OrderType;
import org.contoso.stockorderservice.models.dtos.OrderRequestDTO;
import org.contoso.stockorderservice.repositories.OrderRepository;
import org.contoso.stockorderservice.services.dtos.PortfolioTransactionType;
import org.contoso.stockorderservice.services.dtos.PortfolioUpdateRequestDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final PortfolioServiceClient portfolioServiceClient;
    private final MarketDataService marketDataService;

    private final NotificationService notificationService;

    public Order placeOrder(OrderRequestDTO order) {
        BigDecimal userBalance = userServiceClient.getBalance(order.getUserId());

        BigDecimal stockPrice = marketDataService.getStockPrice(order.getStockSymbol());
        BigDecimal totalCost = stockPrice.multiply(BigDecimal.valueOf(order.getQuantity()));

        if (OrderType.BUY.equals(order.getType())) {
            buyStock(order, userBalance, totalCost);

        } else if (OrderType.SELL.equals(order.getType())) {
            sellStock(order, userBalance, totalCost);
        }

        Order completedOrder = createCompletedOrder(order, stockPrice);
        notificationService.notify(order.getUserId(), "Order completed");
        return completedOrder;
    }

    private void sellStock(OrderRequestDTO order, BigDecimal userBalance, BigDecimal totalCost) {
        var stock = portfolioServiceClient.getStockBySymbol(order.getUserId(), order.getStockSymbol());

        if (stock == null || stock.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Insufficient stock quantity");
        }

        userServiceClient.updateBalance(order.getUserId(), userBalance.add(totalCost));

        updateStockQuantityInPortifolio(order, OrderType.SELL);
    }

    private void buyStock(OrderRequestDTO order, BigDecimal userBalance, BigDecimal totalCost) {
        if (userBalance.compareTo(totalCost) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        userServiceClient.updateBalance(order.getUserId(), userBalance.subtract(totalCost));

        updateStockQuantityInPortifolio(order, OrderType.BUY);
    }

    @NotNull
    private Order createCompletedOrder(OrderRequestDTO orderRequestDTO, BigDecimal stockPrice) {
        Order order = orderRequestDTO.toOrder();
        order.setPrice(stockPrice);
        order.setStatus(OrderStatus.COMPLETED);

        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    private void updateStockQuantityInPortifolio(OrderRequestDTO order, OrderType orderType) {
        PortfolioTransactionType transactionType = switch (orderType) {
            case BUY -> PortfolioTransactionType.BUY;
            case SELL -> PortfolioTransactionType.SELL;
        };

        var request = new PortfolioUpdateRequestDTO(order.getStockSymbol(), order.getQuantity(), transactionType);

        portfolioServiceClient.updatePortifolio(order.getUserId(), request);
    }
}
