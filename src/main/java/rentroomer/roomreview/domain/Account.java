package rentroomer.roomreview.domain;

import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.security.UserRole;
import rentroomer.roomreview.security.support.JWTGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@MappedSuperclass
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public Account() {
    }

    public Account(String userId, UserRole userRole) {
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

    public String generateJWT() {
        return JWTGenerator.generate(userId, userRole.getAuthority());
    }

    public Collection<? extends GrantedAuthority> getAuthorityNames() {
        return Collections.singletonList(userRole);
    }
}
