package com.hcc.config;

import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll() // Allow access to public resources
//                .anyRequest().authenticated() // All other requests require authentication
//                .and()
//                .formLogin() // Use form-based authentication
//                .loginPage("/login").permitAll() // Specify custom login page
//                .defaultSuccessUrl("/dashboard", true) // Redirect to /dashboard after successful login
//                .and()
//                .logout() // Configure logout
//                .logoutUrl("/logout") // Specify logout URL
//                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
//                .permitAll()
//                .and()
//                .csrf().disable(); // Disable CSRF protection for simplicity (not recommended for production)
    }

}