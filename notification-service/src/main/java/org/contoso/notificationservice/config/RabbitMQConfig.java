package org.contoso.notificationservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String queueName = "notifications";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }
}
