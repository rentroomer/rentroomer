package rentroomer.roomreview.security.support;

import org.springframework.stereotype.Component;
import rentroomer.roomreview.exceptions.JWTNotFoundException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component

public class JWTCookieManager {
    private static final String COOKIE_NAME_AUTH = "Auth";

    public void setJWTCookie(HttpServletResponse response, String tokenValue) {
        Cookie cookie = new Cookie(COOKIE_NAME_AUTH, tokenValue);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public void deleteJWTCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(request);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Cookie getCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME_AUTH))
                .findAny()
                .get();
    }

    public boolean isExist(HttpServletRequest request) {
        Optional<Cookie[]> maybeCookies = Optional.ofNullable(request.getCookies());
        return maybeCookies.filter(cookies -> Arrays.stream(cookies)
                .anyMatch(cookie -> cookie.getName().equals(COOKIE_NAME_AUTH))).isPresent();
    }
}
