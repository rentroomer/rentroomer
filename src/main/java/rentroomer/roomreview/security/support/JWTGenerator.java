package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JWTGenerator {
    private static final String SIGN_KEY = "test";
    private static final String ISSUER_NAME = "rent-roomer";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_AUTHORITY = "Authority";

    public static String generate(String userId, String authorityName) throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER_NAME)
                .withClaim(CLAIM_KEY_USER_ID, userId)
                .withClaim(CLAIM_KEY_AUTHORITY, authorityName)
                .sign(getAlgorithm());
    }

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SIGN_KEY.getBytes());
    }
}
