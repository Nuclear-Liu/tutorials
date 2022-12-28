package org.hui.security.persistent.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public final class JWTUtils {
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

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT verify = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            LOGGER.error("verify failed.");
            return false;
        }
        return true;
    }

    public static boolean verify(String token, String userName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withClaim("userName", userName)
                    .build();
            DecodedJWT verify = jwtVerifier.verify(token);
        } catch (JWTDecodeException e) {
            LOGGER.error("verify failed.", e);
            return false;
        }
        return true;
    }

    public static Optional<String> getUserName(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return Optional.ofNullable(decodedJWT.getClaim("userName").asString());
    }

    public static long getExpiresTime(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim exp = decodedJWT.getClaim("exp");
        return exp.asDate().getTime();
    }
}
