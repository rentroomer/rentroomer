package rentroomer.roomreview.domain;

import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.security.UserRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Account extends AbstractEntity {

    private String userId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String providerName;

    private Long socialId;

    public Account(long id, String userId, UserRole userRole) {
        super(id);
        this.userId = userId;
        this.userRole = userRole;
    }

    public Account(String name, UserRole userRole, String providerName, Long socialId) {
        this.userId = name;
        this.userRole = userRole;
        this.providerName = providerName;
        this.socialId = socialId;
    }

    public Long getId() {
        return super.getId();
    }

    public String getUserId() {
        return userId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getAuthorityName() {
        return userRole.name();
    }

    public Collection<? extends GrantedAuthority> getAuthorityNames() {
        return Collections.singletonList(userRole);
    }
}
