package rentroomer.roomreview.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchUserRoleExcetion extends AuthenticationException {

    public NoSuchUserRoleExcetion(String msg, Throwable t) {
        super(msg, t);
    }

    public NoSuchUserRoleExcetion(String msg) {
        super(msg);
    }
}
