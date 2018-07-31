package rentroomer.roomreview.dto;

import rentroomer.roomreview.exceptions.UnsupportedProviderException;
import rentroomer.roomreview.security.OAuthProviderInfo;

public class SocialInfoDto {

    private OAuthProviderInfo provider;
    private String token;

    public SocialInfoDto() {
    }

    public OAuthProviderInfo getProvider() {
        return provider;
    }

    public SocialInfoDto setProvider(String provider) throws UnsupportedProviderException {
        this.provider = OAuthProviderInfo.getProviderByName(provider);
        return this;
    }

    public String getToken() {
        return token;
    }

    public SocialInfoDto setToken(String token) {
        this.token = token;
        return this;
    }
}
