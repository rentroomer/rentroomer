package rentroomer.roomreview.domain;

import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;
import rentroomer.roomreview.security.UserRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class SocialAccount extends Account {
    private Long socialId;

    @Enumerated(EnumType.STRING)
    private SocialProvider provider;

    public SocialAccount() {
    }

    public SocialAccount(SocialProperty property, UserRole userRole, SocialProvider provider) {
        super(property.getName(), userRole);
        this.socialId = property.getSocialId();
        this.provider = provider;
    }

    public Long getSocialId() {
        return socialId;
    }

    public SocialProvider getProvider() {
        return provider;
    }
}
