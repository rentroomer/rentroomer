package rentroomer.roomreview.domain;

import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;

import java.util.Collection;

public class SocialAccount extends Account {

    private String socialId;

    public SocialAccount(SocialProperty property, Collection<? extends GrantedAuthority> authorities, SocialProvider provider) {
        super(property.getName(), authorities);
        this.socialId = ""; // property.getSocialID();
        // generate

    }
}
