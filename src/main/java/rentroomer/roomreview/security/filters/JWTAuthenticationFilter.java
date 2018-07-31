package rentroomer.roomreview.security.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import rentroomer.roomreview.exceptions.JWTNotFoundException;
import rentroomer.roomreview.security.tokens.PreJWTLoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static rentroomer.roomreview.security.handlers.SocialLoginSuccessHandler.COOKIE_NAME_AUTH;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public JWTAuthenticationFilter(RequestMatcher matcher, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(matcher);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        PreJWTLoginToken preAuthToken = Arrays.stream(Optional.ofNullable(request.getCookies())
                .orElseThrow(this::notLogin))
                .filter(c -> c.getName().equals(COOKIE_NAME_AUTH))
                .findFirst()
                .map(PreJWTLoginToken::fromCookie)
                .orElseThrow(this::notLogin);

        return super.getAuthenticationManager().authenticate(preAuthToken);
    }

    private AuthenticationException notLogin() {
        return new JWTNotFoundException("로그인한 사용자가 아닙니다");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
