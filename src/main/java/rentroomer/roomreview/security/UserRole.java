package rentroomer.roomreview.security;

import org.springframework.security.core.GrantedAuthority;
import rentroomer.roomreview.exceptions.NoSuchUserRoleExcetion;

import java.util.Arrays;

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

    public static UserRole getRole(String roleName) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.name().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new NoSuchUserRoleExcetion("존재하지않는 권한임"));
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
