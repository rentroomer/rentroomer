package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;

public class PreSocialLoginToken extends UsernamePasswordAuthenticationToken {

    private PreSocialLoginToken(SocialProvider provider, String token) {
        super(provider, token);
    }

    public static PreSocialLoginToken fromSocialInfoDto(SocialInfoDto dto) {
        return new PreSocialLoginToken(dto.getProvider(), dto.getToken());
    }

    public String getEndPoint() {
        SocialProvider provider = (SocialProvider) super.getPrincipal();
        return provider.getEndPoint();
    }

    public Class<? extends SocialProperty> getResponseType() {
        SocialProvider provider = (SocialProvider) super.getPrincipal();
        return provider.getResponseType();
    }

    public String getToken() {
        return String.valueOf(super.getCredentials());
    }
}
