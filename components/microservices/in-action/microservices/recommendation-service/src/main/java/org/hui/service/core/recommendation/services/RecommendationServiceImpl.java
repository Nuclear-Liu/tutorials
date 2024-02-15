package org.hui.service.core.recommendation.services;

import org.hui.service.core.recommendation.Recommendation;
import org.hui.service.core.recommendation.RecommendationService;
import org.hui.service.core.recommendation.persistence.RecommendationEntity;
import org.hui.service.core.recommendation.persistence.RecommendationRepository;
import org.hui.service.exceptions.InvalidInputException;
import org.hui.service.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.FINE;

@RestController
public class RecommendationServiceImpl implements RecommendationService {
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    private final ServiceUtil serviceUtil;
    private final RecommendationRepository repository;
    private final RecommendationMapper mapper;

    public RecommendationServiceImpl(ServiceUtil serviceUtil, RecommendationRepository repository, RecommendationMapper mapper) {
        this.serviceUtil = serviceUtil;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Recommendation> createRecommendation(Recommendation body) {

        if (body.productId() < 1) {
            throw new InvalidInputException("Invalid productId: " + body.productId());
        }

        RecommendationEntity entity = mapper.apiToEntity(body);
        Mono<Recommendation> newEntity = repository.save(entity)
                .log(LOG.getName(), FINE)
                .onErrorMap(DuplicateKeyException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: " + body.productId() + ", Recommendation Id: " + body.recommendationId()))
                .map(mapper::entityToApi);

        return newEntity;
    }

    @Override
    public Flux<Recommendation> getRecommendations(int productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.info("Will get recommendations for product with id={}", productId);

        return repository.findByProductId(productId)
                .log(LOG.getName(), FINE)
                .map(mapper::entityToApi);
    }

    @Override
    public Mono<Void> deleteRecommendations(int productId) {
        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
        return repository.deleteAll(repository.findByProductId(productId));
    }
}
