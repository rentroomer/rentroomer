package rentroomer.roomreview.security.support;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rentroomer.roomreview.security.UserRole;

public class JWTManagerTest {
    private static final Logger log = LoggerFactory.getLogger(JWTManagerTest.class);

    @Test
    public void generate() {
        JWTManager jwtManager = new JWTManager("testKey");
        String jwt = jwtManager.generate("javajigi", UserRole.getBasicRole().getAuthority());
        log.debug("current : {}", System.currentTimeMillis());
        log.debug("jwt : {}", jwt);
    }
}