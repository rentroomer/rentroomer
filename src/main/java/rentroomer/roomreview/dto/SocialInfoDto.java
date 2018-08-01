package rentroomer.roomreview.dto;

import rentroomer.roomreview.exceptions.UnsupportedProviderException;

public class SocialInfoDto {

    private String provider;
    private String token;

    public SocialInfoDto() {
    }

    public String getProvider() {
        return provider;
    }

    public SocialInfoDto setProvider(String provider) throws UnsupportedProviderException {
        this.provider = provider;
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
