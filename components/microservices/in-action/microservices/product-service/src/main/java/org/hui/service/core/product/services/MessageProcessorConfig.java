package org.hui.service.core.product.services;

import org.hui.service.core.product.Product;
import org.hui.service.core.product.ProductService;
import org.hui.service.event.Event;
import org.hui.service.exceptions.EventProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageProcessorConfig {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorConfig.class);
    private final ProductService productService;

    public MessageProcessorConfig(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public Consumer<Event<Integer, Product>> messageProcessor() {
        return event -> {
            LOG.info("Processor message created at {}...", event.eventCreatedAt());

            switch (event.eventType()) {
                case CREATE -> {
                    Product product = event.data();
                    LOG.info("Create product with ID: {}", product.productId());
                    productService.createProduct(product).block();
                }
                case DELETE -> {
                    int productId = event.key();
                    LOG.info("Delete product with ProductID: {}", productId);
                    productService.deleteProduct(productId).block();
                }
                default -> {
                    String errorMessage = "Incorrect event type: " + event.eventType() + ", expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }
            LOG.info("Message processing done!");
        };
    }
}
