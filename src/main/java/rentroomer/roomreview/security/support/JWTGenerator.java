package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;

import static rentroomer.roomreview.security.support.ExpireTime.THREE_HOURS;
import static rentroomer.roomreview.security.support.ExpireTime.getExpirationDate;

@Component
public class JWTGenerator {

    private String signingKey;

    public JWTGenerator(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }

    private static final String ISSUER_NAME = "rent-roomer";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_AUTHORITY = "Authority";


    public String generate(String userId, String authorityName) throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER_NAME)
                .withExpiresAt(getExpirationDate(THREE_HOURS))
                .withClaim(CLAIM_KEY_USER_ID, userId)
                .withClaim(CLAIM_KEY_AUTHORITY, authorityName)
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(signingKey.getBytes());
    }
}
