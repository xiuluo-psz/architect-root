package com.architect.rabbitmq.controller;

import com.architect.rabbitmq.config.RabbitmqDelayConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.architect.rabbitmq.config.RabbitmqConfig;
@RestController
@RequestMapping(value = "/rabbitmq")
public class PublisherController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("dlx")
    public void demo() {
//        String msg = "设置过期时间10s";
//        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setExpiration("10000").build();
//        rabbitTemplate.send(RabbitmqConfig.DIRECT_EXCHANGE_NAME, RabbitmqConfig.ROUTING_KEY, message);
        rabbitTemplate.convertAndSend(RabbitmqConfig.TEMP_EXCHANGE_NAME, RabbitmqConfig.TEMP_ROUTING_KEY, "dlx message");
    }

    @GetMapping("delay")
    public void delay() {
        Message msg = MessageBuilder.withBody(("延迟队列" + new Date()).getBytes(StandardCharsets.UTF_8))
                        .setHeader("x-delay", 3000).build();
        rabbitTemplate.convertAndSend(RabbitmqDelayConfig.DELAY_EXCHANGE_NAME, RabbitmqDelayConfig.ROUTING_KEY, msg);
    }

    @GetMapping("publish")
    public String publish(String queueName, String msg) {
        rabbitTemplate.convertAndSend(queueName, msg);
        return "向" + queueName + "发送消息： " + msg;
    }

    @GetMapping("direct")
    public String direct(String exchange, String routingKey, String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        return "向routing_key为" + routingKey + "的交换机" + exchange + "发送消息： " + msg;
    }

    @GetMapping("fanout")
    public String fanout(String exchange, String msg) {
        rabbitTemplate.convertAndSend(exchange, null, msg);
        return "向Fanout交换机" + exchange + "发送消息： " + msg;
    }

    @GetMapping("topic")
    public String fanout(String exchange, String routingKey,String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        return "向routingkey为" + routingKey + "的Topic交换机" + exchange + "发送消息： " + msg;
    }

    @GetMapping("header")
    public String header(String exchange, String msg, String headerKey) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setHeader(headerKey, msg).build();
        rabbitTemplate.convertAndSend(exchange, null, message);
        return "向Header交换机" + exchange + "发送消息： " + msg;
    }

    @GetMapping("pull")
    public String pull() {
        String msg = "";
        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
        try {
            GetResponse resp = channel.basicGet(RabbitmqConfig.TEMP_QUEUE_NAME, false);
            long tag = resp.getEnvelope().getDeliveryTag();
            msg = new String(resp.getBody());
            channel.basicAck(tag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "拉模式消息确认：" + msg;
    }
}
