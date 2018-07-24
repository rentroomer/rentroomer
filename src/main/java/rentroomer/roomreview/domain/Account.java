package rentroomer.roomreview.domain;

import rentroomer.roomreview.security.UserRole;

import javax.persistence.*;

@MappedSuperclass
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

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

    public String getAuthorityNames() {
        return userRole.name();
    }
}
