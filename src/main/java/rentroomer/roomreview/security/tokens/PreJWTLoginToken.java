package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreJWTLoginToken extends UsernamePasswordAuthenticationToken {

    public PreJWTLoginToken(Object principal, Object credentials) {
        super(principal, credentials);
    }


}
