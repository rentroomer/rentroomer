package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;

public class PreSocialLoginToken extends UsernamePasswordAuthenticationToken {

    private PreSocialLoginToken(SocialProvider provider, String token) {
        super(provider, token);
    }

    public static PreSocialLoginToken fromSocialInfoDto(SocialInfoDto dto) {
        return new PreSocialLoginToken(dto.getProvider(), dto.getToken());
    }

    public SocialProvider getProvider() {
        return (SocialProvider) super.getPrincipal();
    }

    public SocialAuthenticationTemplate getTemplate() {
        return getProvider().getTemplate();
    }

    public String getEndPoint() {
        SocialProvider provider = getProvider();
        return provider.getEndPoint();
    }

    public Class<? extends SocialProperty> getResponseType() {
        SocialProvider provider = getProvider();
        return provider.getResponseType();
    }

    public String getToken() {
        return String.valueOf(super.getCredentials());
    }
}
