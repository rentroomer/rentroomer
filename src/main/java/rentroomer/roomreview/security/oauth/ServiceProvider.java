package rentroomer.roomreview.security.oauth;

import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.support.SocialAuthenticationTemplate;

public abstract class ServiceProvider {

    private String endPoint;
    private Class<? extends SocialProperty> responseType;
    private SocialAuthenticationTemplate template;

    public ServiceProvider(String endPoint, Class<? extends SocialProperty> responseType, SocialAuthenticationTemplate template) {
        this.endPoint = endPoint;
        this.responseType = responseType;
        this.template = template;
    }

    public abstract String getName();

    public String getEndPoint() {
        return endPoint;
    }

    public Class<? extends SocialProperty> getResponseType() {
        return responseType;
    }

    public SocialAuthenticationTemplate getTemplate() {
        return template;
    }

    public SocialProperty auth(String accessToken) {
        return template.auth(accessToken, this);
    }
}
