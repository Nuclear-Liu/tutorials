package org.hui.service.composite.product;

import java.util.List;

/**
 * 产品聚合信息.
 *
 * @param productId        产品主键
 * @param name             产品名称
 * @param weight           产品重量
 * @param recommendations  推荐信息
 * @param reviews          评论信息
 * @param serviceAddresses 服务地址信息
 */
public record ProductAggregate(
        int productId,
        String name,
        int weight,
        List<RecommendationSummary> recommendations,
        List<ReviewSummary> reviews,
        ServiceAddresses serviceAddresses) {
}
