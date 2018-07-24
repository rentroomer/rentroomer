package rentroomer.roomreview.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.SocialAccount;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;
import rentroomer.roomreview.service.SocialAccountService;

@Component
public class SocialLoginProvider implements AuthenticationProvider {

    @Autowired
    private SocialAccountService socialAccountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreSocialLoginToken preAuthToken = (PreSocialLoginToken) authentication;

        SocialAuthenticationTemplate template = preAuthToken.getTemplate();
        SocialProperty socialProperty = template.auth(preAuthToken);
        SocialAccount account = socialAccountService.findBySocialId(socialProperty, preAuthToken.getProvider());
        return PostLoginToken.create(account);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreSocialLoginToken.class.isAssignableFrom(authentication);
    }
}
