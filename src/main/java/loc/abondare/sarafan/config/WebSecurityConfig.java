package loc.abondare.sarafan.config;

import loc.abondare.sarafan.service.config.SarafanDelegatingOAut2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(ar -> ar
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(o -> o
                        .userInfoEndpoint(u -> u.oidcUserService(this.oidcUserService()))
                );
    }

    @Bean
    public SarafanDelegatingOAut2UserService oidcUserService() {
        return new SarafanDelegatingOAut2UserService(Collections.singletonList(new OidcUserService()));
    }
}
