package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.domain.Account;

import java.util.Collection;
import java.util.UUID;

public class PostLoginToken extends UsernamePasswordAuthenticationToken {

    private PostLoginToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostLoginToken fromAccount(Account authAccount) throws AuthenticationException {
        return new PostLoginToken(authAccount, UUID.randomUUID().toString(), authAccount.getAuthorityNames());
    }

    public Account getAccount() {
        return (Account) super.getPrincipal();
    }
}
