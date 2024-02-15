package org.hui.service.core;

import org.hui.service.core.product.Product;
import org.junit.jupiter.api.Test;

class LombokTest {
    @Test
    void test() {
        Product aaa = Product.builder().productId(1).name("aaa").weight(1).build();
        System.out.println(aaa);
    }
}
