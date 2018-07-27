package rentroomer.roomreview.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.security.support.JWTGenerator;
import rentroomer.roomreview.security.tokens.PostLoginToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Autowired
    private JWTGenerator generator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostLoginToken token = (PostLoginToken) authentication;
        response.setStatus(HttpStatus.OK.value());

        Account account = token.getAccount();
        sendJWT(response, account);
    }

    private void sendJWT(HttpServletResponse response, Account account) {
        Cookie cookie = new Cookie("auth", account.generateJWT(generator));
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
