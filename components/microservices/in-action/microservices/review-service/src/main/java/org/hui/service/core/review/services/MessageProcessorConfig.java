package org.hui.service.core.review.services;

import org.hui.service.core.review.Review;
import org.hui.service.core.review.ReviewService;
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
    private final ReviewService reviewService;

    public MessageProcessorConfig(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Bean
    public Consumer<Event<Integer, Review>> messageProcessor() {
        return event -> {
            LOG.info("Process message created at {}...", event.eventCreatedAt());

            switch (event.eventType()) {
                case CREATE -> {
                    Review review = event.data();
                    LOG.info("Create review with ID: {}/{}", review.productId(), review.reviewId());
                    reviewService.createReview(review).block();
                }
                case DELETE -> {
                    int productId = event.key();
                    LOG.info("Delete reviews with ProductID: {}", productId);
                    reviewService.deleteReviews(productId).block();
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
