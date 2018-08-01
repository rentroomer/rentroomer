package rentroomer.roomreview.security.support;

import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.oauth.ServiceProvider;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

public interface SocialAuthenticationTemplate {

    SocialProperty auth(String accessToken, ServiceProvider serviceProvider);
}
