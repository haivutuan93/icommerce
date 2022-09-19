package com.nab.icommerce.service;

import com.nab.icommerce.entity.Product;
import com.nab.icommerce.mapper.ProductMapper;
import com.nab.icommerce.repository.mongodb.ProductInformationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    private final ProductInformationRepository productInformationRepository;
    private final ProductMapper productMapper;

    public RabbitMQConsumer(ProductInformationRepository productInformationRepository, ProductMapper productMapper) {
        this.productInformationRepository = productInformationRepository;
        this.productMapper = productMapper;
    }

    @RabbitListener(queues = "${icommerce.rabbitmq.queue}")
    public void receivedMessage(Product product) {
        productInformationRepository.save(productMapper.mapToProductInformation(product));

        System.out.println("Saved success productInformation From RabbitMQ: " + product.getName());
    }
}