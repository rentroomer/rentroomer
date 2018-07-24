package rentroomer.roomreview.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.AccountRepository;
import rentroomer.roomreview.domain.SocialAccount;
import rentroomer.roomreview.domain.SocialAccountRepository;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.support.KakaoAuthenticationTemplate;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;
import rentroomer.roomreview.security.tokens.PostLoginToken;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;
import rentroomer.roomreview.service.SocialAccountService;

import javax.annotation.Resource;
import java.text.Normalizer;

@Component
public class SocialLoginProvider implements AuthenticationProvider {

    @Autowired
    private SocialAccountService socialAccountService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreSocialLoginToken preAuthToken = (PreSocialLoginToken) authentication;

        SocialAuthenticationTemplate template = preAuthToken.getTemplate();
        SocialProperty socialProperty = template.auth(preAuthToken);

        //TODO: 인증 받은 후, 유효기간 검사. 유효기간이 지났으면 DB에 조회를 해서 있는 사용자면 조회된 권한 사용. 아니면 기본 권한으로 JWT 발급.
        SocialAccount account = socialAccountService.findBySocialId(socialProperty, preAuthToken.getProvider());
        return PostLoginToken.create();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreSocialLoginToken.class.isAssignableFrom(authentication);
    }
}
