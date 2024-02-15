package org.hui.service.core.review.services;

import org.hui.service.core.review.Review;
import org.hui.service.core.review.persistence.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)})
    Review entityToApi(ReviewEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)})
    ReviewEntity apiToEntity(Review api);

    List<Review> entitiesToApis(List<ReviewEntity> entities);

    List<ReviewEntity> apisToEntities(List<Review> apis);
}
