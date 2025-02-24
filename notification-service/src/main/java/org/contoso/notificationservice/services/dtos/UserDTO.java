package org.contoso.notificationservice.services.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;

    private String name;
    private String email;
    private BigDecimal balance = BigDecimal.ZERO;
}
