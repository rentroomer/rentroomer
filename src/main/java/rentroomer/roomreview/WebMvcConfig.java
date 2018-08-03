package rentroomer.roomreview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rentroomer.roomreview.resolvers.LoginRequiredMethodArgumentsResolver;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginRequiredMethodArgumentsResolver loginRequiredMethodArgumentsResolver() {
        return new LoginRequiredMethodArgumentsResolver();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/login").setViewName("/auth/login");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/reviews/form").setViewName("/html/reviews/form.html");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginRequiredMethodArgumentsResolver());
    }
}
