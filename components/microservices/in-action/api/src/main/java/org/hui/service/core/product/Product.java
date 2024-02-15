package org.hui.service.core.product;

import lombok.Builder;

/**
 * 产品信息.
 *
 * @param productId      产品主键
 * @param name           产品名称
 * @param weight         产品重量
 * @param serviceAddress 服务地址
 */
@Builder
public record Product(
        int productId,
        String name,
        int weight,
        String serviceAddress) {
}
