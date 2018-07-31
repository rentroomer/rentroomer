package rentroomer.roomreview.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.exceptions.JsonConversionFailureException;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public SocialLoginFilter(String url, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(url);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        SocialInfoDto socialInfoDto = null;
        try {
            socialInfoDto = new ObjectMapper().readValue(request.getReader(), SocialInfoDto.class);
        } catch (Exception e) {
            throw new JsonConversionFailureException("인증 정보가 올바르지 않습니다.");
        }
        PreSocialLoginToken token = PreSocialLoginToken.fromSocialInfoDto(socialInfoDto);
        return super.getAuthenticationManager().authenticate(token);
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
