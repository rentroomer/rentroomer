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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rentroomer.roomreview.security.filters.JwtAuthenticationFilter;
import rentroomer.roomreview.security.filters.SocialLoginFilter;
import rentroomer.roomreview.security.providers.SocialLoginProvider;
import rentroomer.roomreview.security.support.JwtSkipMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SocialLoginProvider socialLoginProvider;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;


    private SocialLoginFilter createSocialFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth", successHandler, failureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    private JwtAuthenticationFilter createJwtAuthenticationFilter() throws Exception {
        JwtSkipMatcher skipMatcher = new JwtSkipMatcher("/**", Arrays.asList("/", "/login", "/oauth"));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipMatcher, successHandler, failureHandler);
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
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/js/**")
                .antMatchers("/css/**");
    }
}
