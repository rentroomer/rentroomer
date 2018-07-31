package rentroomer.roomreview.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.exceptions.JWTExpiredException;
import rentroomer.roomreview.security.tokens.PreJWTLoginToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static rentroomer.roomreview.security.handlers.SocialLoginSuccessHandler.COOKIE_NAME_AUTH;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("auth error : {}", exception.getMessage());

        Optional.ofNullable(request.getCookies())
                .ifPresent(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(COOKIE_NAME_AUTH))
                        .findFirst()
                        .ifPresent(cookie -> cookie.setMaxAge(0))
                );

        response.sendRedirect("/login");
    }
}
