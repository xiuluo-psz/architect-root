package com.architect.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqDlxConfig {

    public static final String DLX_QUEUE_NAME = "dlx_queue";
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String ROUTING_KEY = "dlx_routing_key";

    @Bean
    Queue createDlxQueue() {
        return new Queue(DLX_QUEUE_NAME, true, false, false);
    }

    @Bean
    DirectExchange createDlxExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding dlxBinding() {
        return BindingBuilder.bind(createDlxQueue()).to(createDlxExchange()).with(ROUTING_KEY);
    }

}
