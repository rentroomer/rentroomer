package rentroomer.roomreview.security.tokens;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.exceptions.JsonConversionFailureException;

import java.util.Collection;
import java.util.Collections;

public class PostLoginToken extends UsernamePasswordAuthenticationToken {

    private PostLoginToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostLoginToken create(Account authAccount) throws AuthenticationException {
        try {
            String token = new ObjectMapper().writeValueAsString(authAccount);
            return new PostLoginToken(authAccount.getUserId(), token, Collections.singletonList(authAccount.getUserRole()));
        } catch (Exception e) {
            throw new JsonConversionFailureException("나중에 이름 짓기");
        }
    }
}
