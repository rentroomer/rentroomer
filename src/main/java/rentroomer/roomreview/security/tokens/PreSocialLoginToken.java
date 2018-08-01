package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.oauth.ServiceProvider;

public class PreSocialLoginToken extends UsernamePasswordAuthenticationToken {

    private PreSocialLoginToken(ServiceProvider provider, String token) {
        super(provider, token);
    }

    public static PreSocialLoginToken fromAccessToken(ServiceProvider serviceProvider, String token) {
        return new PreSocialLoginToken(serviceProvider, token);
    }

    public ServiceProvider getProvider() {
        return (ServiceProvider) super.getPrincipal();
    }

    public String getProviderName() {
        return getProvider().getName();
    }

    public String getEndPoint() {
        return getProvider().getEndPoint();
    }

    public Class<? extends SocialProperty> getResponseType() {
        return getProvider().getResponseType();
    }

    public String getAccessToken() {
        return String.valueOf(super.getCredentials());
    }

    public SocialProperty getSocialProperty() {
        ServiceProvider provider = getProvider();
        return provider.auth(getAccessToken());
    }
}
