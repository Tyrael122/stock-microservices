package org.contoso.stockorderservice.services;

import org.contoso.stockorderservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notify(UUID userId, String message) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, userId + "/" + message);
    }
}
