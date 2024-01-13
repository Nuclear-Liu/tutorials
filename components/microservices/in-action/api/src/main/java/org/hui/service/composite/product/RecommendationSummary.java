package org.hui.service.composite.product;

/**
 * 推荐摘要.
 *
 * @param recommendationId 推荐主键
 * @param author           推荐人
 * @param rate             推荐率
 */
public record RecommendationSummary(
        int recommendationId,
        String author,
        int rate,
        String content) {
}
