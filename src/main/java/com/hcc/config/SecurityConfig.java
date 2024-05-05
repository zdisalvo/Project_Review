package com.hcc.config;

import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(customPasswordEncoder.getPasswordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        http

                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/api/auth/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

}

//package com.hcc.config;
//
//        import com.hcc.filters.jwtFilter;
//        import com.hcc.services.UserDetailServiceImpl;
//        import com.hcc.utils.CustomPasswordEncoder;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.security.authentication.AuthenticationManager;
//        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//        import org.springframework.security.config.http.SessionCreationPolicy;
//        import org.springframework.security.core.userdetails.User;
//        import org.springframework.security.core.userdetails.UserDetails;
//        import org.springframework.security.core.userdetails.UserDetailsService;
//        import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//        import javax.servlet.http.HttpServletResponse;
//
//@EnableWebSecurity
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserDetailServiceImpl userDetailServiceImpl;
//
//    @Autowired
//    CustomPasswordEncoder customPasswordEncoder;
//
//    @Autowired
//    jwtFilter jwtFilt;
//
//    @Override @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//        @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(customPasswordEncoder.getPasswordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors().disable(); // do not dissable this lot here just for now!!
////
////        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
//
//        http = http.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
//        }).and();
//
////        http.authorizeRequests()
////                .antMatchers("/api/auth/**").permitAll()
////                .antMatchers("/").permitAll()
////                .antMatchers("/createuser").permitAll()
////                .antMatchers("/mydashboard").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .and()
////                .logout()
////                .permitAll();
//        //http.addFilterBefore(jwtFilt, UsernamePasswordAuthenticationFilter.class);
//
//                http
//
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/createuser").permitAll()
//                .antMatchers("/api/assignments").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/api/auth/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//
//                http.addFilterBefore(jwtFilt, UsernamePasswordAuthenticationFilter.class);
//    }
//
//}