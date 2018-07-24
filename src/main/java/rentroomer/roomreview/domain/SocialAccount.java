package rentroomer.roomreview.domain;

import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;
import rentroomer.roomreview.security.UserRole;

import javax.persistence.Entity;

@Entity
public class SocialAccount extends Account {

    private String socialId;

    public SocialAccount(SocialProperty property, UserRole userRole, SocialProvider provider) {
        super(property.getName(), userRole);
        this.socialId = ""; // TODO : 수정하기 property.getSocialID();
        // generate

    }
}
