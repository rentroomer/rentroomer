package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JWTGenerator.class);

    @Value("${jwt.signing.key}")
    private String signingKey;

    private static final String ISSUER_NAME = "rent-roomer";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_AUTHORITY = "Authority";


    public String generate(String userId, String authorityName) throws JWTCreationException {
        logger.debug("key : {}", signingKey);
        return JWT.create()
                .withIssuer(ISSUER_NAME)
                .withClaim(CLAIM_KEY_USER_ID, userId)
                .withClaim(CLAIM_KEY_AUTHORITY, authorityName)
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(signingKey.getBytes());
    }
}
