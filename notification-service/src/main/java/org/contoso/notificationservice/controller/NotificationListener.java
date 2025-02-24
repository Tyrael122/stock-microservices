package org.contoso.notificationservice.controller;

import org.contoso.notificationservice.config.RabbitMQConfig;
import org.contoso.notificationservice.services.NotificationService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void receiveMessage(Message message) {
        System.out.println("Received message: " + message + " at " + LocalDateTime.now());

        String messageBody = new String(message.getBody());
        System.out.println("Message body: " + messageBody);

        String[] parts = messageBody.split("/");

        System.out.println(Arrays.toString(parts));

        notificationService.handleNotification(UUID.fromString(parts[0]), parts[1]);
    }
}
