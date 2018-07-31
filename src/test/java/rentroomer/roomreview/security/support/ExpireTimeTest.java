package rentroomer.roomreview.security.support;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpireTimeTest {
    private static final Logger logger = LoggerFactory.getLogger(ExpireTimeTest.class);

    @Test
    public void getExpirationDate() {
        Date time = ExpireTime.getExpirationDate(ExpireTime.THREE_HOURS);
        assertTrue(time.after(new Date()));
    }
}