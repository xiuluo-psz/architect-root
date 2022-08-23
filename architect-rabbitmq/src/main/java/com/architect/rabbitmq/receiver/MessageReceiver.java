package com.architect.rabbitmq.receiver;

import com.architect.rabbitmq.config.RabbitmqConfig;
import com.architect.rabbitmq.config.RabbitmqDelayConfig;
import com.architect.rabbitmq.config.RabbitmqDlxConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class MessageReceiver {

    @RabbitListener(queues = RabbitmqConfig.DEMO_QUEUE_NAME)
    public void handleDemoMessage(String msg) {
        System.out.println(RabbitmqConfig.DEMO_QUEUE_NAME + "接受消息：" + msg);
    }

    @RabbitListener(queues = RabbitmqConfig.HELLO_WORLD_QUEUE_NAME)
    public void handleMessageHelloWorld(String msg) {
        System.out.println(RabbitmqConfig.HELLO_WORLD_QUEUE_NAME + "接受消息：" + msg);
    }

    @RabbitListener(queues = RabbitmqConfig.WORK_QUEUE_NAME)
    public void handleMessageWork(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.WORK_QUEUE_NAME + "接受消息：" + message.getPayload());
        // 我不消费。b标识表示拒绝的消息是否重新进入队列
        channel.basicReject((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), true);
    }

    // concurrency 并发数量
    @RabbitListener(queues = RabbitmqConfig.WORK_QUEUE_NAME, concurrency = "2")
    public void handleMessageWork2(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.WORK_QUEUE_NAME + "2接受并消费消息：" + message.getPayload());
        // 我消费
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_DIRECT_QUEUE_NAME)
    public void handleMessageDirect(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_DIRECT_QUEUE_NAME + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_DIRECT_QUEUE_NAME_2)
    public void handleMessageDirect2(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_DIRECT_QUEUE_NAME_2 + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_FANOUT_QUEUE_NAME)
    public void handleMessageFanout(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_FANOUT_QUEUE_NAME + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_FANOUT_QUEUE_NAME_2)
    public void handleMessageFanout2(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_FANOUT_QUEUE_NAME_2 + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_TOPIC_XIAOMI_QUEUE_NAME)
    public void handleMessageTopic(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_TOPIC_XIAOMI_QUEUE_NAME + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_TOPIC_HUAWEI_QUEUE_NAME)
    public void handleMessageTopic2(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_TOPIC_HUAWEI_QUEUE_NAME + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_TOPIC_PHONE_QUEUE_NAME)
    public void handleMessageTopic3(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_TOPIC_PHONE_QUEUE_NAME + "接受并消费消息：" + message.getPayload());
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_HEADER_NAME_QUEUE_NAME)
    public void handleMessageHeaderName(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_HEADER_NAME_QUEUE_NAME + "接受并消费消息：" + new String((byte[])message.getPayload()));
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqConfig.PS_HEADER_AGE_QUEUE_NAME)
    public void handleMessageHeaderAge(Message<Object> message, Channel channel) throws IOException {
        System.out.println(RabbitmqConfig.PS_HEADER_AGE_QUEUE_NAME + "接受并消费消息：" + new String((byte[])message.getPayload()));
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqDlxConfig.DLX_QUEUE_NAME)
    public void handleMessageDlx(Message<Object> message, Channel channel) throws IOException {
//        System.out.println("死信队列接受消息：" + msg);
        String msg = (String)(message.getPayload());
        System.out.println(new Date() + " 死信队列接受消息：" + msg);
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitmqDelayConfig.DELAY_QUEUE_NAME)
    public void handleMessageDelay(Message<Object> message, Channel channel) throws IOException {
        String msg = new String((byte[]) message.getPayload());
        System.out.println(new Date() + " 延迟队列接受消息：" + msg);
        channel.basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
    }
}
