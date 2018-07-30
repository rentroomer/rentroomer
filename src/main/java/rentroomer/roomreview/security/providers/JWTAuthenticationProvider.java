package rentroomer.roomreview.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.security.support.JWTManager;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreJWTLoginToken;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JWTManager jwtManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreJWTLoginToken preAuthToken = (PreJWTLoginToken) authentication;
        Account account = jwtManager.decode(preAuthToken.getTokenValue());
        return PostLoginToken.create(account);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreJWTLoginToken.class.isAssignableFrom(authentication);
    }
}
