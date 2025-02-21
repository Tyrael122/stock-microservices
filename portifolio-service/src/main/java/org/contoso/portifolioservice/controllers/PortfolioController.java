package org.contoso.portifolioservice.controllers;

import org.contoso.portifolioservice.dtos.PortfolioUpdateRequestDTO;
import org.contoso.portifolioservice.dtos.StockDTO;
import org.contoso.portifolioservice.services.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{userId}/stocks/{symbol}")
    public StockDTO getStockBySymbol(@PathVariable UUID userId, @PathVariable String symbol) {
        return portfolioService.getStockBySymbol(userId, symbol);
    }

    @PostMapping("/{userId}/stocks")
    public void updatePortfolio(@PathVariable UUID userId, @RequestBody PortfolioUpdateRequestDTO request) {
        portfolioService.updatePortfolio(userId, request);
    }
}
