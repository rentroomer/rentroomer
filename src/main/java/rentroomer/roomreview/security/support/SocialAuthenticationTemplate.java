package rentroomer.roomreview.security.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

public class SocialAuthenticationTemplate {
    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final HttpMethod AUTH_METHOD = HttpMethod.GET;

    public static SocialProperty auth(PreSocialLoginToken token) {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(createHeader(token));
        return template.exchange(token.getEndPoint(), AUTH_METHOD, entity, token.getResponseType()).getBody();
    }

    private static HttpHeaders createHeader(PreSocialLoginToken token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_KEY, AUTH_HEADER_PREFIX + token.getToken());
        return headers;
    }
}
