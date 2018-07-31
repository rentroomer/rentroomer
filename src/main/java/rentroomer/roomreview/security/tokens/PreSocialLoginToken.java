package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.OAuthProviderInfo;

public class PreSocialLoginToken extends UsernamePasswordAuthenticationToken {

    private PreSocialLoginToken(OAuthProviderInfo provider, String token) {
        super(provider, token);
    }

    public static PreSocialLoginToken fromSocialInfoDto(SocialInfoDto dto) {
        return new PreSocialLoginToken(dto.getProvider(), dto.getToken());
    }

    public OAuthProviderInfo getProvider() {
        return (OAuthProviderInfo) super.getPrincipal();
    }

    public String getEndPoint() {
        OAuthProviderInfo provider = getProvider();
        return provider.getEndPoint();
    }

    public Class<? extends SocialProperty> getResponseType() {
        OAuthProviderInfo provider = getProvider();
        return provider.getResponseType();
    }

    public String getToken() {
        return String.valueOf(super.getCredentials());
    }

    public SocialProperty getSocialProperty() {
        OAuthProviderInfo provider = getProvider();
        return provider.getSocialProperty(this);
    }
}
