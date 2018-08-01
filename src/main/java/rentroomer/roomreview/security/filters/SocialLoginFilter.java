package rentroomer.roomreview.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.exceptions.JsonConversionFailureException;
import rentroomer.roomreview.security.handlers.SocialLoginSuccessHandler;
import rentroomer.roomreview.security.oauth.ServiceProvider;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SocialLoginSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private ApplicationContext beanContainer;

    public SocialLoginFilter() {
        super("/oauth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            SocialInfoDto socialInfoDto = new ObjectMapper().readValue(request.getReader(), SocialInfoDto.class);
            ServiceProvider serviceProvider = (ServiceProvider) beanContainer.getBean(socialInfoDto.getProvider());
            PreSocialLoginToken token = PreSocialLoginToken.fromAccessToken(serviceProvider, socialInfoDto.getToken());
            return super.getAuthenticationManager().authenticate(token);
        } catch (Exception e) {
            throw new JsonConversionFailureException("인증 정보가 올바르지 않습니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
