package org.hui.service.composite.product.service;

import org.hui.service.composite.product.*;
import org.hui.service.core.product.Product;
import org.hui.service.core.recommendation.Recommendation;
import org.hui.service.core.review.Review;
import org.hui.service.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeServiceImpl.class);
    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration integration;

    public ProductCompositeServiceImpl(ServiceUtil serviceUtil, ProductCompositeIntegration integration) {
        this.serviceUtil = serviceUtil;
        this.integration = integration;
    }

    @Override
    public Mono<Void> createProduct(ProductAggregate body) {
        try {

            List<Mono<?>> monos = new ArrayList<>();

            LOG.info("Will create a new composite entity for product.id: {}", body.productId());

            Product product = new Product(body.productId(), body.name(), body.weight(), null);
            monos.add(integration.createProduct(product));

            if (Objects.nonNull(body.recommendations())) {
                body.recommendations().forEach(r -> {
                    Recommendation recommendation = new Recommendation(body.productId(), r.recommendationId(), r.author(), r.rate(), r.content(), null);
                    monos.add(integration.createRecommendation(recommendation));
                });
            }

            if (Objects.nonNull(body.reviews())) {
                body.reviews().forEach(r -> {
                    Review review = new Review(body.productId(), r.reviewId(), r.author(), r.subject(), r.content(), null);
                    monos.add(integration.createReview(review));
                });
            }

            LOG.debug("createCompositeProduct: composite entities created for productId: {}", body.productId());

            return Mono.zip(r -> "r",
                            monos.toArray(new Mono[0]))
                    .doOnError(ex -> LOG.warn("createComposite Product failed: {}", ex.toString()))
                    .then();
        } catch (RuntimeException re) {
            LOG.warn("createCompositeProduct failed", re);
            throw re;
        }
    }

    @Override
    public Mono<ProductAggregate> getProduct(int productId) {

        LOG.debug("getCompositeProduct: lookup a product aggregate for productId: {}", productId);
        return Mono.zip(values ->
                                createProductAggregate(
                                        (Product) values[0],
                                        (List<Recommendation>) values[1],
                                        (List<Review>) values[2], serviceUtil.getServiceAddress()),
                        integration.getProduct(productId),
                        integration.getRecommendations(productId).collectList(),
                        integration.getReviews(productId).collectList())
                .doOnError(ex -> LOG.warn("getCompositeProduct failed: {}", ex.toString()))
                .log(LOG.getName(), FINE);
    }

    @Override
    public Mono<Void> deleteProduct(int productId) {
        try {
            LOG.info("Will delete a product aggregate for product.id: {}", productId);

            return Mono.zip(r -> "",
                            integration.deleteProduct(productId),
                            integration.deleteRecommendations(productId),
                            integration.deleteReviews(productId))
                    .doOnError(ex -> LOG.warn("delete failed: {}", ex.toString()))
                    .log(LOG.getName(), FINE)
                    .then();
        } catch (RuntimeException re) {
            LOG.warn("deleteCompositeProduct failed: {}", re.toString());
            throw re;
        }
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {
        // 1. Setup product info
        int productId = product.productId();
        String name = product.name();
        int weight = product.weight();

        // 2. Copy summary recommendation info, if available
        List<RecommendationSummary> recommendationSummaries = (Objects.isNull(recommendations)) ? null : recommendations
                .stream()
                .map(r -> new RecommendationSummary(r.recommendationId(), r.author(), r.rate(), r.content()))
                .collect(Collectors.toList());

        // 3. Copy summary review info, if available
        List<ReviewSummary> reviewSummaries = (Objects.isNull(reviews)) ? null : reviews
                .stream()
                .map(r -> new ReviewSummary(r.reviewId(), r.author(), r.subject(), r.content()))
                .collect(Collectors.toList());

        // 4. Create info regarding the involved microservices address
        String productAddress = product.serviceAddress();
        String reviewAddress = (reviews != null && !reviews.isEmpty()) ? reviews.get(0).serviceAddress() : "";
        String recommendationAddress = (recommendations != null && !recommendations.isEmpty()) ? recommendations.get(0).serviceAddress() : "";
        ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);

        return new ProductAggregate(productId, name, weight, recommendationSummaries, reviewSummaries, serviceAddresses);
    }
}
