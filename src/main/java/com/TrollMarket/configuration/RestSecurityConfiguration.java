package com.TrollMarket.configuration;

import com.TrollMarket.component.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {
    private  final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public RestSecurityConfiguration(JwtRequestFilter jwtRequestFilter){
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.securityMatcher("/api/**")
                .csrf(cf -> cf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/authenticate","/api/account/authenticate").permitAll()
                        .requestMatchers("/api/profile/balance").hasAnyAuthority("Seller", "Buyer")
                        .requestMatchers("/api/my-cart/**").hasAuthority("Buyer")
                        .requestMatchers("/api/shop/**").hasAuthority("Buyer")
                        .requestMatchers( "/api/shipper/**").hasAuthority("Admin")
                        .requestMatchers( "/api/merchandise/**").hasAuthority("Seller")
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
                ).httpBasic(basic -> basic
                        .authenticationEntryPoint(authenticationEntryPoint())
                ).exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                ).cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                );
        return httpSecurity.build();
    }


    private AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized request header");
        };
    }

    private AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access Denied");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
