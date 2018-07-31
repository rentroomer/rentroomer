package rentroomer.roomreview.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnsupportedProviderException extends AuthenticationException {

    public UnsupportedProviderException(String message) {
        super(message);
    }
}
