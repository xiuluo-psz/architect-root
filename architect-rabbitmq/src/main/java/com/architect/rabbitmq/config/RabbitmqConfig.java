package com.architect.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    public static final String DEMO_QUEUE_NAME = "demo_queue";

    public static final String HELLO_WORLD_QUEUE_NAME = "hello_world_queue";

    public static final String WORK_QUEUE_NAME = "work_queue";

    public static final String PS_DIRECT_QUEUE_NAME = "direct_queue";
    public static final String PS_DIRECT_QUEUE_NAME_2 = "direct_queue_2";
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    public static final String ROUTING_KEY = "direct_routing_key";
    public static final String ROUTING_KEY_2 = "direct_routing_key_2";

    public static final String PS_FANOUT_QUEUE_NAME = "fanout_queue";
    public static final String PS_FANOUT_QUEUE_NAME_2 = "fanout_queue_2";
    public static final String FANOUT_EXCHANGE_NAME = "fanout_exchange";

    public static final String PS_TOPIC_XIAOMI_QUEUE_NAME = "xiaomi_queue";
    public static final String PS_TOPIC_HUAWEI_QUEUE_NAME = "huawei_queue";
    public static final String PS_TOPIC_PHONE_QUEUE_NAME = "phone_queue";
    public static final String TOPIC_EXCHANGE_NAME = "topic_exchange";
    public static final String XIAOMI_ROUTING_KEY = "xiaomi.#";
    public static final String HUAWEI_ROUTING_KEY = "huawei.#";
    public static final String PHONE_ROUTING_KEY = "#.phone.#";

    public static final String PS_HEADER_NAME_QUEUE_NAME = "name_queue";
    public static final String PS_HEADER_AGE_QUEUE_NAME = "age_queue";
    public static final String HEADER_EXCHANGE_NAME = "header_exchange";

    public static final String TEMP_QUEUE_NAME = "temp_queue";
    public static final String TEMP_EXCHANGE_NAME = "temp_exchange";
    public static final String TEMP_ROUTING_KEY = "temp_routing_key";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initTemplate() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Bean
    Queue createDemoQueue() {
        // params1: 队列名
        // params2: 持久化
        // params3: 队列是否具有排他性
        // params4: 如果队列没有消费者，是否自动删除该队列
        return new Queue(DEMO_QUEUE_NAME, true, false, false);
    }

    // Hello World
    @Bean
    Queue createHelloWorldQueue() {
        return new Queue(HELLO_WORLD_QUEUE_NAME, true, false, false);
    }

    // Work queues
    @Bean
    Queue createWorkQueue() {
        return new Queue(WORK_QUEUE_NAME, true, false, false);
    }

    // Publish/Subscribe
    // Direct
    @Bean
    Queue createDirectQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000); // 设置队列消息过期时间
        return new Queue(PS_DIRECT_QUEUE_NAME, true, false, false, args);
    }

    @Bean
    Queue createDirectQueue2() {
        return new Queue(PS_DIRECT_QUEUE_NAME_2, true, false, false);
    }

    @Bean
    DirectExchange createDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding directBinding() {
        return BindingBuilder.bind(createDirectQueue()).to(createDirectExchange()).with(ROUTING_KEY);
    }

    @Bean
    Binding directBinding2() {
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(ROUTING_KEY_2);
    }

    // Publish/Subscribe
    // Fanout
    @Bean
    Queue createFanoutQueue() {
        return new Queue(PS_FANOUT_QUEUE_NAME, true, false, false);
    }

    @Bean
    Queue createFanoutQueue2() {
        return new Queue(PS_FANOUT_QUEUE_NAME_2, true, false, false);
    }

    @Bean
    FanoutExchange createFanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding fanoutBinding() {
        return BindingBuilder.bind(createFanoutQueue()).to(createFanoutExchange());
    }

    @Bean
    Binding fanoutBinding2() {
        return BindingBuilder.bind(createFanoutQueue2()).to(createFanoutExchange());
    }

    // Publish/Subscribe
    // Topic
    @Bean
    Queue createTopicXiaomiQueue() {
        return new Queue(PS_TOPIC_XIAOMI_QUEUE_NAME, true, false, false);
    }

    @Bean
    Queue createTopicHuaweiQueue() {
        return new Queue(PS_TOPIC_HUAWEI_QUEUE_NAME, true, false, false);
    }

    @Bean
    Queue createTopicPhoneQueue() {
        return new Queue(PS_TOPIC_PHONE_QUEUE_NAME, true, false, false);
    }

    @Bean
    TopicExchange createTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding xiaomiBinding() {
        return BindingBuilder.bind(createTopicXiaomiQueue()).to(createTopicExchange()).with(XIAOMI_ROUTING_KEY);
    }

    @Bean
    Binding huaweiBinding() {
        return BindingBuilder.bind(createTopicHuaweiQueue()).to(createTopicExchange()).with(HUAWEI_ROUTING_KEY);
    }

    @Bean
    Binding phoneBinding() {
        return BindingBuilder.bind(createTopicPhoneQueue()).to(createTopicExchange()).with(PHONE_ROUTING_KEY);
    }

    // Publish/Subscribe
    // Header
    @Bean
    Queue createHeaderNameQueue() {
        return new Queue(PS_HEADER_NAME_QUEUE_NAME, true, false, false);
    }

    @Bean
    Queue createHeaderAgeQueue() {
        return new Queue(PS_HEADER_AGE_QUEUE_NAME, true, false, false);
    }

    @Bean
    HeadersExchange createHeaderExchange() {
        return new HeadersExchange(HEADER_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding nameBinding() {
        return BindingBuilder.bind(createHeaderNameQueue()).to(createHeaderExchange()).where("name").exists();
    }

    @Bean
    Binding ageBinding() {
        return BindingBuilder.bind(createHeaderAgeQueue()).to(createHeaderExchange()).where("age").matches("99");
    }

    // 设置无消费者队列，测试死信队列
    @Bean
    Queue create4dlxQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3 * 1000); // 设置队列消息过期时间
        args.put("x-dead-letter-exchange", RabbitmqDlxConfig.DLX_EXCHANGE_NAME); // 设置死信队列交换机
        args.put("x-dead-letter-routing-key", RabbitmqDlxConfig.ROUTING_KEY); // 设置死信队列routing-key
        return new Queue(TEMP_QUEUE_NAME, true, false, false, args);
    }

    @Bean
    DirectExchange create4dlxExchange() {
        return new DirectExchange(TEMP_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding forDlxBinding() {
        return BindingBuilder.bind(create4dlxQueue()).to(create4dlxExchange()).with(TEMP_ROUTING_KEY);
    }

    // 消息到达交换机的回调
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息到达交换机的回调");
    }

    // 消息没有到达队列的回调
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息没有到达队列的回调");
    }
}
