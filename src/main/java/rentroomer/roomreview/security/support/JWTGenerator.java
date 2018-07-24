package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JWTGenerator {
    private static final String SIGN_KEY = "test";

    public static String generate() throws JWTCreationException {
        return JWT.create()

                .sign(getAlgorithm());
    }

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SIGN_KEY.getBytes());
    }
}
