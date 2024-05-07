//package com.hcc.config;
//
//import com.hcc.services.UserDetailServiceImpl;
//import com.hcc.utils.CustomPasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserDetailServiceImpl userDetailServiceImpl;
//
//    @Autowired
//    CustomPasswordEncoder customPasswordEncoder;
//
//    @Bean
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
////    @Autowired
////    PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //super.configure(http);
//
//        http
//
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/api/auth/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//}

package com.hcc.config;

        import com.hcc.filters.jwtFilter;
        import com.hcc.services.UserDetailServiceImpl;
        import com.hcc.utils.CustomPasswordEncoder;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
        import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.provisioning.InMemoryUserDetailsManager;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

        import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    jwtFilter jwtFilt;

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

        @Bean
    public UserDetailsService userDetailsService() {
        UserDetails learner = User.builder()
                .username("learner")
                .password(customPasswordEncoder.getPasswordEncoder().encode("password"))
                .roles("LEARNER")
                .build();

        UserDetails reviewer = User.builder()
                .username("reviewer")
                .password(customPasswordEncoder.getPasswordEncoder().encode("password"))
                .roles("REVIEWER") // Set reviewer role
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(customPasswordEncoder.getPasswordEncoder().encode("password"))
                .roles("ADMIN") // Set reviewer role
                .build();

        return new InMemoryUserDetailsManager(learner, reviewer, admin);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable(); // do not dissable this lot here just for now!!
//
//        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        http = http.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        }).and();

//        http.authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/createuser").permitAll()
//                .antMatchers("/mydashboard").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .permitAll();
        //http.addFilterBefore(jwtFilt, UsernamePasswordAuthenticationFilter.class);

                http

                .authorizeRequests()
                        .antMatchers("/api/assignments/review").hasRole("REVIEWER")
                        .antMatchers("/api/assignments/review/{\\d+}").hasRole("REVIEWER")
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/createuser").permitAll()
                .antMatchers("/api/assignments").permitAll()
                .antMatchers("/api/editassignment").permitAll()
                .antMatchers("/api/assignments/{\\d+}").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/api/auth/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/api/auth/login?logout") // Redirect to login page after logout
                .permitAll();

                http.addFilterBefore(jwtFilt, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_REVIEWER > ROLE_LEARNER");
        return roleHierarchy;
    }

}