package org.contoso.stockorderservice.services;

import org.contoso.stockorderservice.services.dtos.StockDTO;
import org.contoso.stockorderservice.services.dtos.PortfolioUpdateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "portfolio-service")
public interface PortfolioServiceClient {

    @GetMapping("/portfolio/{userId}/stocks/{symbol}")
    StockDTO getStockBySymbol(@PathVariable("userId") UUID userId, @PathVariable("symbol") String symbol);

    @PostMapping("/portfolio/{userId}/stocks")
    void updatePortifolio(@PathVariable UUID userId, @RequestBody PortfolioUpdateRequestDTO request);
}
