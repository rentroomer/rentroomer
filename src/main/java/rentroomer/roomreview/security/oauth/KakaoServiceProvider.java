package rentroomer.roomreview.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.dto.KakaoProperty;
import rentroomer.roomreview.security.support.KakaoAuthenticationTemplate;

@Component(value = "KAKAO")
public class KakaoServiceProvider extends ServiceProvider {

    public KakaoServiceProvider(@Value("${oauth.endpoint.kakao}") String endPoint, @Autowired KakaoAuthenticationTemplate template) {
        super(endPoint, KakaoProperty.class, template);
    }

    @Override
    public String getName() {
        return "KAKAO";
    }
}
