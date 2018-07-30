package rentroomer.roomreview.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JWTVerificationFailureException extends AuthenticationException {

    public JWTVerificationFailureException(String msg) {
        super(msg);
    }

    public JWTVerificationFailureException(String msg, Throwable t) {
        super(msg, t);
    }
}
