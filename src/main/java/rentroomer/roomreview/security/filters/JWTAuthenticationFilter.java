package rentroomer.roomreview.security.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import rentroomer.roomreview.exceptions.JWTNotFoundException;
import rentroomer.roomreview.security.support.JWTCookieManager;
import rentroomer.roomreview.security.tokens.PreJWTLoginToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private JWTCookieManager cookieManager;

    public JWTAuthenticationFilter(RequestMatcher matcher, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, JWTCookieManager cookieManager) {
        super(matcher);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.cookieManager = cookieManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!cookieManager.isExist(request)) {
            throw new JWTNotFoundException("쿠키 없음");
        }
        PreJWTLoginToken preAuthToken = PreJWTLoginToken.fromCookie(cookieManager.getCookie(request));
        return super.getAuthenticationManager().authenticate(preAuthToken);
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
