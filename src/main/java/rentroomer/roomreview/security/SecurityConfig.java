package rentroomer.roomreview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rentroomer.roomreview.security.filters.JWTAuthenticationFilter;
import rentroomer.roomreview.security.filters.SocialLoginFilter;
import rentroomer.roomreview.security.handlers.JWTAuthenticationSuccessHandler;
import rentroomer.roomreview.security.handlers.SocialLoginSuccessHandler;
import rentroomer.roomreview.security.providers.JWTAuthenticationProvider;
import rentroomer.roomreview.security.providers.SocialLoginProvider;
import rentroomer.roomreview.security.support.JWTSkipMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SocialLoginProvider socialLoginProvider;

    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private SocialLoginSuccessHandler socialLoginSuccessHandler;

    @Autowired
    private JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    private SocialLoginFilter createSocialFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth", socialLoginSuccessHandler, failureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    private JWTAuthenticationFilter createJwtAuthenticationFilter() throws Exception {
        JWTSkipMatcher skipMatcher = new JWTSkipMatcher("/**", Arrays.asList("/login", "/oauth"));
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(skipMatcher, jwtAuthenticationSuccessHandler, failureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilterBefore(createSocialFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(createJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(socialLoginProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/js/**")
                .antMatchers("/css/**")
                .antMatchers("/favicon.ico");
    }
}
