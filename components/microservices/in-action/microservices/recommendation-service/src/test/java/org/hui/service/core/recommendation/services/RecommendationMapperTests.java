package org.hui.service.core.recommendation.services;

import org.hui.service.core.recommendation.Recommendation;
import org.hui.service.core.recommendation.persistence.RecommendationEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationMapperTests {
    private RecommendationMapper mapper = Mappers.getMapper(RecommendationMapper.class);

    @Test
    void mapperTests() {

        assertNotNull(mapper);

        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");

        RecommendationEntity entity = mapper.apiToEntity(api);

        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.recommendationId(), entity.getRecommendationId());
        assertEquals(api.author(), entity.getAuthor());
        assertEquals(api.rate(), entity.getRating());
        assertEquals(api.content(), entity.getContent());

        Recommendation api2 = mapper.entityToApi(entity);

        assertEquals(api.productId(), api2.productId());
        assertEquals(api.recommendationId(), api2.recommendationId());
        assertEquals(api.author(), api2.author());
        assertEquals(api.rate(), api2.rate());
        assertEquals(api.content(), api2.content());
        assertNull(api2.serviceAddress());
    }

    @Test
    void mapperListTests() {

        assertNotNull(mapper);

        Recommendation api = new Recommendation(1, 2, "a", 4, "C", "adr");
        List<Recommendation> apis = Collections.singletonList(api);

        List<RecommendationEntity> entities = mapper.apisToEntities(apis);
        assertEquals(apis.size(), entities.size());

        RecommendationEntity entity = entities.get(0);

        assertEquals(api.productId(), entity.getProductId());
        assertEquals(api.recommendationId(), entity.getRecommendationId());
        assertEquals(api.author(), entity.getAuthor());
        assertEquals(api.rate(), entity.getRating());
        assertEquals(api.content(), entity.getContent());

        List<Recommendation> apis2 = mapper.entitiesToApis(entities);
        assertEquals(apis.size(), apis2.size());

        Recommendation api2 = apis2.get(0);

        assertEquals(api.productId(), api2.productId());
        assertEquals(api.recommendationId(), api2.recommendationId());
        assertEquals(api.author(), api2.author());
        assertEquals(api.rate(), api2.rate());
        assertEquals(api.content(), api2.content());
        assertNull(api2.serviceAddress());
    }
}