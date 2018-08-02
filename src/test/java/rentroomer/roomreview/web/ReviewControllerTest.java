package rentroomer.roomreview.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.security.UserRole;
import rentroomer.roomreview.security.support.JWTManager;
import rentroomer.roomreview.support.RequestBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ReviewControllerTest.class);

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private JWTManager jwtManager;

    private RequestBuilder builder;
    private String jwt;

    @Before
    public void setUp() throws Exception {
        jwt = jwtManager.generate(new Account(1L, "testID", UserRole.USER));
        builder = new RequestBuilder();
    }

    @Test
    public void form() {
        ResponseEntity<String> response = template.getForEntity("/reviews/form", String.class);

        logger.debug(response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().toLowerCase().contains("review form page"));
    }

    @Test
    public void create_LOGGED_IN_BUT_NO_AUTHORIZATION() {
        ResponseEntity<String> response = template.postForEntity("/reviews/", builder.build(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void create_AUTHORIZED() {
        builder.addHeader("Cookie", "Auth=" + jwt);
        builder.addParam("cost", 60);
        builder.addParam("deposit", 1000);
        builder.addParam("structure", "원룸");
        ResponseEntity<String> response = template.postForEntity("/reviews/", builder.build(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        logger.debug(response.getBody());
    }
}