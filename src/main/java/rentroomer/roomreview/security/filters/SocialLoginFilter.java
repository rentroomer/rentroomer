package rentroomer.roomreview.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import rentroomer.roomreview.dto.SocialInfoDto;
import rentroomer.roomreview.exceptions.UnsupportedProviderException;
import rentroomer.roomreview.security.tokens.PreSocialLoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

    public SocialLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        PreSocialLoginToken token = null;
        try {
            SocialInfoDto socialInfoDto = new ObjectMapper().readValue(request.getReader(), SocialInfoDto.class);
             token = PreSocialLoginToken.fromSocialInfoDto(socialInfoDto);
        } catch (UnsupportedProviderException e) {
            unsuccessfulAuthentication(request, response, e);
        }
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
