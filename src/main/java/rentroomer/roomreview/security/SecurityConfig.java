package rentroomer.roomreview.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rentroomer.roomreview.security.filters.JWTAuthenticationFilter;
import rentroomer.roomreview.security.filters.SocialLoginFilter;
import rentroomer.roomreview.security.providers.JWTAuthenticationProvider;
import rentroomer.roomreview.security.providers.SocialLoginProvider;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Autowired
    private SocialLoginProvider socialLoginProvider;

    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.csrf().disable();
        http.headers().frameOptions().disable();
        /*http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);*/
        http.addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(socialLoginProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login", "/oauth", "/h2-console/**", "/js/**", "/css/**", "/favicon.ico", "/reviews/form");
    }
}
