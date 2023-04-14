package org.hui.security.persistent.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JWTUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtilsTest.class);

    @Test
    void testCreateToken() {
        String token = JWTUtils.createToken("admin");
        assertNotNull(token);
        LOGGER.info(token);
    }

    @Test
    void testVerify() {
        String token = JWTUtils.createToken("admin");

        assertTrue(JWTUtils.verify(token));
    }

}