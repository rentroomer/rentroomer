package rentroomer.roomreview.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public static UserRole getBasicRole() {
        return USER;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
