package rentroomer.roomreview.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import rentroomer.roomreview.security.oauth.ServiceProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContainerTest {

    @Autowired
    private ApplicationContext beanContainer;

    @Test
    public void getBean() {
        ServiceProvider serviceProvider = (ServiceProvider) beanContainer.getBean("KAKAO");
        assertThat(serviceProvider.getName(), is("KAKAO"));
    }
}
