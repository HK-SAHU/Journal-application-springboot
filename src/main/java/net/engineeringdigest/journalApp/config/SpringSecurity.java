package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/journal/**").authenticated()   // this ** is the wildcard entry mtlb /journal ke baad kuvh bhi aa jaye wo
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        /*ye toh endpoints ko save kar liya but user ke username and password ko save karne ke liye journal entry
        controller mein jake username and password as header mein dena padega */
    }
}
