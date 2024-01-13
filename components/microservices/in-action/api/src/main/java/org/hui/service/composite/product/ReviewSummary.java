package org.hui.service.composite.product;

/**
 * 评论摘要.
 *
 * @param reviewId 评论主键
 * @param author   评论人
 * @param subject  评论主题
 */
public record ReviewSummary(
        int reviewId,
        String author,
        String subject,
        String content) {
}
