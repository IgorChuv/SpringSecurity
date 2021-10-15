package com.works.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().withUser("Igor").password(encoder.encode("1234")).roles("ALL")
                .and().withUser("Alex").password(encoder.encode("1234")).roles("NOT_ALL");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/persons/by-age").hasRole("ALL")
                .and().authorizeRequests().antMatchers("/persons/by-name-and-surname").hasAnyRole("ALL","NOT_ALL")
                .and().authorizeRequests().antMatchers("/hello").permitAll()
                .and().formLogin();

    }
}
