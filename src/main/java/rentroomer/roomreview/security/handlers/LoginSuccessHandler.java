package rentroomer.roomreview.security.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.security.support.JWTManager;
import rentroomer.roomreview.security.tokens.PostLoginToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    public static final String COOKIE_NAME_AUTH = "auth";

    @Autowired
    private JWTManager generator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostLoginToken token = (PostLoginToken) authentication;
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Location", "/");

        Account account = token.getAccount();
        sendJWT(response, account);
    }

    private void sendJWT(HttpServletResponse response, Account account) {
        Cookie cookie = new Cookie(COOKIE_NAME_AUTH, generator.generate(account));
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
