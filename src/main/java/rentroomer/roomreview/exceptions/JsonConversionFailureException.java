package rentroomer.roomreview.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JsonConversionFailureException extends AuthenticationException {

    public JsonConversionFailureException(String msg, Throwable t) {
        super(msg, t);
    }

    public JsonConversionFailureException(String msg) {
        super(msg);
    }
}
