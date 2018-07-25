package rentroomer.roomreview.domain;

import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;
import rentroomer.roomreview.security.UserRole;

import javax.persistence.Entity;

@Entity
public class SocialAccount extends Account {
    private Long socialId;

    public SocialAccount() {
    }

    public SocialAccount(SocialProperty property, UserRole userRole, SocialProvider provider) {
        super(property.getName(), userRole);
        this.socialId = property.getSocialId(); // TODO : provider 합쳐서 등록하기
    }

    public Long getSocialId() {
        return socialId;
    }

    public SocialAccount setSocialId(Long socialId) {
        this.socialId = socialId;
        return this;
    }
}
