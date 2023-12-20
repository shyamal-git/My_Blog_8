package com.myblog8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

//in this class we configure which url which user can access.
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
//            .and()
//            .logout() // Configure logout
//                .logoutUrl("/api/auth/logout") // Custom logout URL for your API
//                .logoutSuccessHandler(logoutSuccessHandler()) // Use a custom logout success handler
//            .and()
//            .exceptionHandling()
//                .accessDeniedPage("/access-denied");
    }
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
//        successHandler.setTargetUrlParameter("logoutSuccessUrl"); // Define the URL parameter for the logout success URL
//
//        successHandler.setUseReferer(true); // Use the referer header if the target URL is not provided
//        successHandler.setDefaultTargetUrl("/api/auth/logout/success"); // Set a default success URL
//
//        return successHandler;
//    }
//   // IN MEMORY AUTHENTICATION
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("test").password(passwordEncoder()
//                .encode("test")).roles("USER").build();
//
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
