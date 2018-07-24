package rentroomer.roomreview.security.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

@Component
public class SocialLoginProvider implements AuthenticationProvider {

    // 데이터베이스

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreSocialLoginToken token = (PreSocialLoginToken) authentication;
        SocialProperty socialProperty = SocialAuthenticationTemplate.auth(token);


        return PostLoginToken.create();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreSocialLoginToken.class.isAssignableFrom(authentication);
    }
}
