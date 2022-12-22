package org.hui.security.persistent.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JWTUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

    private static final long EXPIRES_TIME = 1000 * 60 * 60 * 24;

    private static final String SECRET_KEY = "hui.liu";

    public static String createToken(String userName) {
        long currentTimestamp = System.currentTimeMillis();
        Date expiresDate = new Date(currentTimestamp + EXPIRES_TIME);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withClaim("userName", userName)
                .withExpiresAt(expiresDate)
                .sign(algorithm);
    }
}
