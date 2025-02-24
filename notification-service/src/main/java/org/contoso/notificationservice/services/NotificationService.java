package org.contoso.notificationservice.services;

import lombok.RequiredArgsConstructor;
import org.contoso.notificationservice.services.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserServiceClient userServiceClient;
    private final EmailService emailService;

    public void handleNotification(UUID userId, String message) {
        UserDTO user = userServiceClient.getUser(userId);

        System.out.println("Found user: " + user);

        emailService.sendEmail(user.getEmail(), "From the microservices Spring Boot application", message);
    }
}
