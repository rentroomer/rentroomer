package rentroomer.roomreview.dto;

import rentroomer.roomreview.exceptions.UnsupportedProviderException;
import rentroomer.roomreview.security.SocialProvider;

public class SocialInfoDto {

    private SocialProvider provider;
    private String token;

    public SocialInfoDto() {
    }

    public SocialProvider getProvider() {
        return provider;
    }

    public SocialInfoDto setProvider(String provider) throws UnsupportedProviderException {
        this.provider = SocialProvider.getProviderByName(provider);
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
