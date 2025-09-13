package com.myreflectionthoughts.user.security;

import com.myreflectionthoughts.user.dataprovider.service.auth.JwtAuthenticationProvider;
import com.myreflectionthoughts.user.filter.JwtFilter;
import com.myreflectionthoughts.user.utility.JwtUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityConfig {

    private final JwtUtility jwtUtility;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfig(JwtUtility jwtUtility, JwtAuthenticationProvider jwtAuthenticationProvider){
        this.jwtUtility = jwtUtility;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

      JwtFilter jwtFilter = new JwtFilter(jwtUtility, jwtAuthenticationProvider);

      return  httpSecurity
                .authorizeHttpRequests(httpRequest->
                        httpRequest.requestMatchers(
                                "/api/users/**",
                                          "/v3/api-docs/**",
                                          "/swagger-ui/**",
                                          "/swagger-ui/index.html")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public ProviderManager setProviders(){
        return new ProviderManager(List.of(jwtAuthenticationProvider));
    }
}
