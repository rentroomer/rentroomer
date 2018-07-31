package rentroomer.roomreview.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.domain.AccountRepository;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import static rentroomer.roomreview.security.UserRole.getBasicRole;

@Component
public class SocialLoginProvider implements AuthenticationProvider {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreSocialLoginToken preAuthToken = (PreSocialLoginToken) authentication;
        SocialProperty socialProperty = preAuthToken.getSocialProperty();

        Account account = accountRepository.findBySocialIdAndProviderInfo(socialProperty.getSocialId(), preAuthToken.getProvider())
                .orElseGet(() -> accountRepository.save(new Account(socialProperty.getName(), getBasicRole(), preAuthToken.getProvider(), socialProperty.getSocialId())));

        return PostLoginToken.fromAccount(account);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreSocialLoginToken.class.isAssignableFrom(authentication);
    }
}
