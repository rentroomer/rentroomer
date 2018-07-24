package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import rentroomer.roomreview.domain.Account;

public class JWTGenerator {
    private static final String SIGN_KEY = "test";
    private static final String CLAIM_KEY_AUTHORITIES = "Authorities";

    public static String generate(Account account) throws JWTCreationException {
        return JWT.create()
                .withIssuer(account.getUserId())
                .withClaim(CLAIM_KEY_AUTHORITIES, account.getAuthorityNames())
                .sign(getAlgorithm());
    }

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SIGN_KEY.getBytes());
    }
}
