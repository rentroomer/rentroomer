package rentroomer.roomreview.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialLoginAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(SocialLoginAcceptanceTest.class);

    @Value("${oauth.test.access.token}")
    private String accessToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void oauthLogin() {
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/oauth", createRequest(), Void.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(response.getHeaders().get("Set-Cookie").get(0));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));

        logger.debug("HEADERS: {}", response.getHeaders().toString());
        logger.debug("Set-Cookie: {}", response.getHeaders().get("Set-Cookie").get(0));

        String location = response.getHeaders().getLocation().getPath();
        ResponseEntity<String> homepage = testRestTemplate.getForEntity(location, String.class);
        logger.debug(homepage.getBody());
    }

    private HttpEntity<String> createRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String body = "{\"provider\": \"KAKAO\", \"token\": "+ "\"" + accessToken + "\"" + "}";

        return new HttpEntity<>(body, headers);
    }
}
