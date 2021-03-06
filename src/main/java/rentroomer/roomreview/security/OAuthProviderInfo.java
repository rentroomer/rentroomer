package rentroomer.roomreview.security;

import rentroomer.roomreview.dto.KakaoProperty;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.exceptions.UnsupportedProviderException;
import rentroomer.roomreview.security.support.KakaoAuthenticationTemplate;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import java.util.Arrays;

public enum OAuthProviderInfo {
    KAKAO("https://kapi.kakao.com/v2/user/me", KakaoProperty.class, new KakaoAuthenticationTemplate());

    private String endPoint;
    private Class<? extends SocialProperty> responseType;
    private SocialAuthenticationTemplate template;

    OAuthProviderInfo(String endPoint, Class<? extends SocialProperty> responseType, SocialAuthenticationTemplate template) {
        this.endPoint = endPoint;
        this.responseType = responseType;
        this.template = template;
    }

    public static OAuthProviderInfo getProviderByName(String provider) throws UnsupportedProviderException {
        return Arrays.stream(values()).filter(p -> p.name().equals(provider)).findFirst().orElseThrow(() -> new UnsupportedProviderException("지원하지 않는 소셜 공급자 입니다."));
    }

    public String getEndPoint() {
        return endPoint;
    }

    public Class<? extends SocialProperty> getResponseType() {
        return responseType;
    }

    public SocialProperty getSocialProperty(PreSocialLoginToken preSocialLoginToken) {
        return template.auth(preSocialLoginToken);
    }
}
