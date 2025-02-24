package org.contoso.stockorderservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "market-data-service")
public interface MarketDataService {

    @GetMapping("/market-data/price/{symbol}")
    BigDecimal getStockPrice(@PathVariable String symbol);
}
