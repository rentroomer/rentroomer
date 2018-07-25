package rentroomer.roomreview.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.dto.TokenDto;
import rentroomer.roomreview.security.support.JWTGenerator;

import java.util.Collection;
import java.util.Collections;

public class PostLoginToken extends UsernamePasswordAuthenticationToken {

    public PostLoginToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostLoginToken create(Account authAccount) throws AuthenticationException {
        TokenDto tokenDto = new TokenDto(JWTGenerator.generate(authAccount));
        return new PostLoginToken(tokenDto, tokenDto.getToken(), Collections.singletonList(authAccount.getUserRole()));
    }

    public TokenDto getJWT() {
        return (TokenDto) super.getPrincipal();
    }
}
