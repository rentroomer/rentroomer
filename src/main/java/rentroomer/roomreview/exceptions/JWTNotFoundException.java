package rentroomer.roomreview.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JWTNotFoundException extends AuthenticationException {

    public JWTNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public JWTNotFoundException(String msg) {
        super(msg);
    }
}
