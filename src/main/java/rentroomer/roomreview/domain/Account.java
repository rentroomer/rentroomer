package rentroomer.roomreview.domain;

import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.security.OAuthProviderInfo;
import rentroomer.roomreview.security.UserRole;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated
    private OAuthProviderInfo OAuthProviderInfo;

    private Long socialId;

    public Account() {
    }

    public Account(String name, UserRole userRole, OAuthProviderInfo provider, Long socialId) {
        this.userId = name;
        this.userRole = userRole;
        this.OAuthProviderInfo = provider;
        this.socialId = socialId;
    }

    public Account(long id, String userId, UserRole userRole) {
        this.id = id;
        this.userId = userId;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
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
