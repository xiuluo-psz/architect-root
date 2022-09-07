package com.architect.kafka.consumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.StringDecoder;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumer {

    @KafkaListener(topics = "heima")
    public void consumerTopic(String msg) {
        System.out.println("收到消息：" + msg);
    }
}
