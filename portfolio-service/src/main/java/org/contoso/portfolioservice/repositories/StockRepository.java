package org.contoso.portfolioservice.repositories;

import org.contoso.portfolioservice.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    Optional<Stock> findByUserIdAndSymbol(UUID userId, String symbol);
}
