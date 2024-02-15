package org.hui.service.core.recommendation;

import lombok.Builder;

/**
 * 推荐信息.
 *
 * @param productId        产品主键
 * @param recommendationId 推荐主键
 * @param author           推荐人
 * @param rate             推荐率
 * @param content          推荐内容
 * @param serviceAddress   服务地址
 */
@Builder
public record Recommendation(
        int productId,
        int recommendationId,
        String author,
        int rate,
        String content,
        String serviceAddress) {
}
