package rentroomer.roomreview.security.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.oauth.ServiceProvider;

@Component
public class KakaoAuthenticationTemplate implements SocialAuthenticationTemplate {
    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final HttpMethod AUTH_METHOD = HttpMethod.GET;

    public SocialProperty auth(String accessToken, ServiceProvider provider) {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(createHeader(accessToken));
        return template.exchange(provider.getEndPoint(), AUTH_METHOD, entity, provider.getResponseType()).getBody();
    }

    private static HttpHeaders createHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_KEY, AUTH_HEADER_PREFIX + accessToken);
        return headers;
    }
}
