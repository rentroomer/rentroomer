package rentroomer.roomreview.security.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.exceptions.JWTExpiredException;
import rentroomer.roomreview.exceptions.JWTVerificationFailureException;
import rentroomer.roomreview.security.UserRole;

import java.util.Date;

import static rentroomer.roomreview.security.support.ExpireTime.THREE_HOURS;
import static rentroomer.roomreview.security.support.ExpireTime.getExpirationDate;

@Component
public class JWTManager {
    private static final String ISSUER_NAME = "rent-roomer";
    private static final String CLAIM_KEY_ID = "id";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_AUTHORITY = "authority";

    private String signingKey;

    public JWTManager(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }

    public String generate(Account account) throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER_NAME)
                .withExpiresAt(getExpirationDate(THREE_HOURS))
                .withClaim(CLAIM_KEY_ID, account.getId())
                .withClaim(CLAIM_KEY_USER_ID, account.getUserId())
                .withClaim(CLAIM_KEY_AUTHORITY, account.getAuthorityName())
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(signingKey.getBytes());
    }

    public Account decode(String encodedToken) {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm()).build()
                    .verify(encodedToken);

            checkExpire(decodedJWT);
            Long id = decodedJWT.getClaim(CLAIM_KEY_ID).asLong();
            String userId = decodedJWT.getClaim(CLAIM_KEY_USER_ID).asString();
            String authority = decodedJWT.getClaim(CLAIM_KEY_AUTHORITY).asString();
            return new Account(id, userId, UserRole.getRole(authority));
        } catch (JWTVerificationException e) {
            throw new JWTVerificationFailureException("JWT 위조함");
        }
    }

    private void checkExpire(DecodedJWT decodedJWT) throws AuthenticationException {
        Date date = decodedJWT.getExpiresAt();
        if (date.before(new Date())) {
            throw new JWTExpiredException("기간 만료됨");
        }
    }
}
