package org.contoso.stockorderservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/users/{userId}/balance")
    BigDecimal getBalance(@PathVariable UUID userId);

    @PutMapping("/users/{userId}/balance")
    void updateBalance(@PathVariable UUID userId, BigDecimal newBalance);
}
