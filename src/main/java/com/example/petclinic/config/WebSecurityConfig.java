package com.example.petclinic.config;


import com.example.petclinic.service.impl.UserDetailsServiceImpl;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/onwers/**").hasAnyAuthority("OWNER_ADMIN")
                .antMatchers("/api/pettypes/**").hasAnyAuthority("VET_ADMIN")
                .antMatchers("/api/pets/**").hasAnyAuthority("OWNER_ADMIN")
                .antMatchers("/api/visits/**").hasAuthority("OWNER_ADMIN")
                .antMatchers("/api/specialties/**").hasAuthority("VET_ADMIN")
                .antMatchers("/api/vets/**").hasAuthority("VET_ADMIN")
                .antMatchers("/admin/users/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable()
        ;
    }
}

