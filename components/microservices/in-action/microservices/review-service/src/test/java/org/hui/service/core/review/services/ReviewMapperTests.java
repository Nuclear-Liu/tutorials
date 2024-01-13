package org.hui.service.core.review.services;

import org.hui.service.core.review.Review;
import org.hui.service.core.review.persistence.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTests {
    private ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void mapperTests() {
        assertNotNull(mapper);

        Review api = new Review(1, 2, "a", "s", "C", "adr");

        ReviewEntity entity = mapper.apiToEntity(api);

        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.reviewId(), entity.getReviewId());
        assertEquals(api.author(), entity.getAuthor());
        assertEquals(api.subject(), entity.getSubject());
        assertEquals(api.content(), entity.getContent());

        Review api2 = mapper.entityToApi(entity);

        assertEquals(api.productId(), api2.productId());
        assertEquals(api.reviewId(), api2.reviewId());
        assertEquals(api.author(), api2.author());
        assertEquals(api.subject(), api2.subject());
        assertEquals(api.content(), api2.content());
        assertNull(api2.serviceAddress());
    }

    @Test
    void mapperListTests() {
        assertNotNull(mapper);

        Review api = new Review(1, 2, "a", "s", "C", "adr");
        List<Review> apis = Collections.singletonList(api);

        List<ReviewEntity> entities = mapper.apisToEntities(apis);
        assertEquals(apis.size(), entities.size());

        ReviewEntity entity = entities.get(0);

        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.reviewId(), entity.getReviewId());
        assertEquals(api.author(), entity.getAuthor());
        assertEquals(api.subject(), entity.getSubject());
        assertEquals(api.content(), entity.getContent());

        List<Review> apis2 = mapper.entitiesToApis(entities);
        assertEquals(apis.size(),apis2.size());

        Review api2 = apis2.get(0);

        assertEquals(api.productId(),api2.productId());
        assertEquals(api.reviewId(),api2.reviewId());
        assertEquals(api.author(),api2.author());
        assertEquals(api.subject(),api2.subject());
        assertEquals(api.content(),api2.content());
        assertNull(api2.serviceAddress());
    }
}