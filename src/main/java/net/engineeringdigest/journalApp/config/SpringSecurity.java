package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/journal/**", "/user/**").authenticated()   // this ** is the wildcard entry mtlb /journal ke baad kuvh bhi aa jaye wo
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();

        /* csrf is the cross site request forgery
            and bby deafult it is enable, and when it remains enable springSecurity expect that wwe will send the
            token(in request) and currently it is stateless and doesn't want the csrf protection

            csrf is a cyber attack in which a malacious website or a program can trick us to submit a request that we don't want to

            and here it is stateless that's why it is disabled here, server to server call hongi session ka koi issue nhi hai
            but springSecurity session manage karta hai isliye usse bhi disable kar diya hai
        */

        /*
        Configuring Authorization Rules
        ye toh endpoints ko save kar liya but user ke username and password ko save karne ke liye journal entry
        controller mein jake username and password as header mein dena padega */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /*
    Configuring Authentication (How users are validated)

    yaha pe ho rha hai kaam user, password, authentication related
    jaise hi app postman mein username password dalenge toh ye dekhna ki userDetailsService kaunsi banai hui hai
    kaise mein usename se user laa sakta hu aur fir usse password matching wala process start kar sakta hu (passwordEncoder())
    aur password hashed form mein save hoga ab

    userDetailsService(userDetailsService):
➔ Tell Spring: "Go to my UserDetailsServiceImpl to fetch user details when someone tries to log in."

passwordEncoder(passwordEncoder()):
➔ Passwords in the database are encrypted (hashed).
➔ When user logs in, it will encrypt entered password and compare with saved encrypted password.

     */

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    Defining the Password Encoder Bean
    jab hum user ko save kar rahe honge tab hum encoder ka use karenge jisse wo hashed mein save ho jaye
    to jab hum login karenge tab toh normal password denge toh wahi password encoder handle kar lega
    postman mein diye gye password ko hash mein covert karega and then dono hash ko compare karega (db and login)
     */
}
