package org.contoso.marketdataservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class MarketDataService {

    private static class StockData {
        double price;
        long lastUpdated;

        StockData(double price, long lastUpdated) {
            this.price = price;
            this.lastUpdated = lastUpdated;
        }
    }

    private final Map<String, StockData> stockPrices = new ConcurrentHashMap<>();
    private static final long UPDATE_INTERVAL = 5000; // 5 seconds in milliseconds

    public double getStockPrice(String symbol) {
        return stockPrices.compute(symbol, (key, data) -> {
            long currentTime = System.currentTimeMillis();
            if (data == null || currentTime - data.lastUpdated >= UPDATE_INTERVAL) {
                double newPrice = generateNewPrice(data);
                return new StockData(newPrice, currentTime);
            }
            return data;
        }).price;
    }

    private double generateNewPrice(StockData data) {
        if (data == null) {
            return ThreadLocalRandom.current().nextDouble(50.0, 500.0);
        }
        return ThreadLocalRandom.current().nextDouble(data.price * 0.95, data.price * 1.05);
    }
}
