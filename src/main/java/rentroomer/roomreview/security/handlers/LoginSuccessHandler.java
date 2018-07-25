package rentroomer.roomreview.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.security.tokens.PostLoginToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostLoginToken token = (PostLoginToken) authentication;
        response.setStatus(HttpStatus.OK.value());
        String jwt = token.getJWT().getToken();

        Cookie cookie = new Cookie("auth", jwt);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
