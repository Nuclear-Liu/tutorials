package org.hui.service.core.recommendation.services;

import org.hui.service.core.recommendation.Recommendation;
import org.hui.service.core.recommendation.RecommendationService;
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
    private final RecommendationService recommendationService;

    public MessageProcessorConfig(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Bean
    public Consumer<Event<Integer, Recommendation>> messageProcessor() {
        return event -> {
            LOG.info("Process message created at {}...", event.eventCreatedAt());

            switch (event.eventType()) {
                case CREATE -> {
                    Recommendation recommendation = event.data();
                    LOG.info("Create recommendation with ID: {}/{}", recommendation.productId(), recommendation.recommendationId());
                    recommendationService.createRecommendation(recommendation).block();
                }
                case DELETE -> {
                    int productId = event.key();
                    LOG.info("Delete recommendations with ProductID: {}", productId);
                    recommendationService.deleteRecommendations(productId).block();
                }
                default -> {
                    String errorMessage = "Incorrect event type: " + event.eventType() + ". expected a CREATE or DELETE event";
                    LOG.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            LOG.info("Message processing done!");
        };
    }
}
