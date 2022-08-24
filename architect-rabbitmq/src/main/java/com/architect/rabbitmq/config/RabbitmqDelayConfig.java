package com.architect.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqDelayConfig {

    public static final String DELAY_QUEUE_NAME = "delay_queue";
    public static final String DELAY_EXCHANGE_NAME = "delay_exchange";
    public static final String ROUTING_KEY = "delay_routing_key";

    @Bean
    Queue createDelayQueue() {
        return new Queue(DELAY_QUEUE_NAME, true, false, false);
    }

    @Bean
    CustomExchange createDelayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    Binding delayBinding() {
        return BindingBuilder.bind(createDelayQueue()).to(createDelayExchange()).with(ROUTING_KEY).noargs();
    }

}
