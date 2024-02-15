package org.hui.service.core.review;

import lombok.Builder;

/**
 * 评论信息.
 *
 * @param productId      产品主键
 * @param reviewId       评价主键
 * @param author         评价人
 * @param subject        主题
 * @param content        内容
 * @param serviceAddress 服务地址
 */
@Builder
public record Review(
        int productId,
        int reviewId,
        String author,
        String subject,
        String content,
        String serviceAddress) {
}
