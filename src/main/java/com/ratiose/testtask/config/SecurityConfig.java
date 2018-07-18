package com.ratiose.testtask.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable().
                authorizeRequests().
                    antMatchers("/user/register").permitAll().
                    antMatchers("/actor/*").hasRole("USER").
                    antMatchers("/movie/*").hasRole("USER").
                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().httpBasic();
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("myTest@email.com")
                .password("pass")
                .roles("USER");
    }
}
