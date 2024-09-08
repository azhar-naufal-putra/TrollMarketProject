package com.TrollMarket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/resources/**","/css/**", "/js/**", "/fonts/**", "/image/**", "/account/login", "/account/register", "/account/access-denied", "/account/fail-login").permitAll()
                .requestMatchers("/merchandise", "/merchandise/**").hasAuthority("Seller")
                .requestMatchers("/history", "/history/**").hasAuthority("Admin")
                .requestMatchers("/admin", "/admin/**").hasAuthority("Admin")
                .requestMatchers("/shipper", "/shipper/**").hasAuthority("Admin")
                .requestMatchers("/my-cart", "/my-cart/**").hasAuthority("Buyer")
                .requestMatchers("/shop", "/shop/**").hasAuthority("Buyer")
                .requestMatchers("/profile").hasAnyAuthority("Buyer", "Seller")
                .anyRequest().authenticated()
        ).formLogin(login -> login
                .loginPage("/account/login")
                .loginProcessingUrl("/authenticating")
                .failureUrl("/account/fail-login")
                .defaultSuccessUrl("/", true)
        ).logout(logout -> logout
                .logoutUrl("/logout")
        ).exceptionHandling(exception -> exception
                .accessDeniedPage("/account/access-denied")
        );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
