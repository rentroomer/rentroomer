package rentroomer.roomreview.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.SocialAccount;
import rentroomer.roomreview.domain.SocialAccountRepository;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import static rentroomer.roomreview.security.UserRole.getBasicRole;

@Component
public class SocialLoginProvider implements AuthenticationProvider {

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreSocialLoginToken preAuthToken = (PreSocialLoginToken) authentication;
        SocialProperty socialProperty = preAuthToken.getSocialProperty();
        SocialAccount account = socialAccountRepository.findBySocialIdAndProvider(socialProperty.getSocialId(), preAuthToken.getProvider())
                .orElseGet(() -> socialAccountRepository.save(new SocialAccount(socialProperty, getBasicRole(), preAuthToken.getProvider())));

        return PostLoginToken.create(account);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreSocialLoginToken.class.isAssignableFrom(authentication);
    }
}
