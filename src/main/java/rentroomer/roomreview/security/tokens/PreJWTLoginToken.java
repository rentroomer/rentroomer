package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.Cookie;

public class PreJWTLoginToken extends UsernamePasswordAuthenticationToken {

    private PreJWTLoginToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public static PreJWTLoginToken fromCookie(Cookie jwtCookie) {
        return new PreJWTLoginToken(jwtCookie, jwtCookie.getValue());
    }

    public String getTokenValue() {
        return String.valueOf(super.getCredentials());
    }
}
