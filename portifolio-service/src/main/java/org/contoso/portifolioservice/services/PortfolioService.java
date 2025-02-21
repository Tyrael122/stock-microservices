package org.contoso.portifolioservice.services;

import org.contoso.portifolioservice.dtos.PortfolioTransactionType;
import org.contoso.portifolioservice.dtos.PortfolioUpdateRequestDTO;
import org.contoso.portifolioservice.dtos.StockDTO;
import org.contoso.portifolioservice.models.Stock;
import org.contoso.portifolioservice.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PortfolioService {

    private final StockRepository stockRepository;

    public PortfolioService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Fetch stock by symbol and userId
    public StockDTO getStockBySymbol(UUID userId, String symbol) {
        Stock stock = stockRepository.findByUserIdAndSymbol(userId, symbol)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        return new StockDTO(stock.getSymbol(), stock.getName(), stock.getQuantity());
    }

    @Transactional
    public void updatePortfolio(UUID userId, PortfolioUpdateRequestDTO request) {
        // Find stock in portfolio
        Stock stock = stockRepository.findByUserIdAndSymbol(userId, request.getSymbol())
                .orElseGet(() -> {
                    // If the stock does not exist, create it
                    Stock newStock = new Stock(userId, request.getSymbol(), "Unknown Stock", 0);
                    stockRepository.save(newStock);
                    return newStock;
                });

        // Update the stock quantity based on transaction type
        if (request.getTransactionType() == PortfolioTransactionType.BUY) {
            stock.setQuantity(stock.getQuantity() + request.getQuantity());
        } else if (request.getTransactionType() == PortfolioTransactionType.SELL) {
            stock.setQuantity(stock.getQuantity() - request.getQuantity());
        }

        // If quantity is 0 or negative, remove the stock entry
        if (stock.getQuantity() <= 0) {
            stockRepository.delete(stock);
        } else {
            stockRepository.save(stock);  // Save the updated stock
        }
    }
}
