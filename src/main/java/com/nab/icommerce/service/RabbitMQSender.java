package com.nab.icommerce.service;

import com.nab.icommerce.entity.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${icommerce.rabbitmq.exchange}")
    private String exchange;

    @Value("${icommerce.rabbitmq.routingkey}")
    private String routingkey;

    public RabbitMQSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Product product) {
        rabbitTemplate.convertAndSend(exchange, routingkey, product);
        System.out.println("Send msg = " + product.getName());

    }
}