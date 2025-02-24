package org.contoso.marketdataservice.controller;

import org.contoso.marketdataservice.service.MarketDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market-data")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/price/{symbol}")
    public double getStockPrice(@PathVariable String symbol) {
        return marketDataService.getStockPrice(symbol.toUpperCase());
    }
}

