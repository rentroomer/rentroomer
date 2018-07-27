package rentroomer.roomreview.security.support;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rentroomer.roomreview.security.UserRole;

public class JWTGeneratorTest {
    private static final Logger log = LoggerFactory.getLogger(JWTGeneratorTest.class);

    @Test
    public void generate() {
        JWTGenerator jwtGenerator = new JWTGenerator("testKey");
        String jwt = jwtGenerator.generate("javajigi", UserRole.getBasicRole().getAuthority());
        log.debug("current : {}", System.currentTimeMillis());
        log.debug("jwt : {}", jwt);
    }
}