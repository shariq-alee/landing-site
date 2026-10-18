package com.shariq.landing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String adminUsername;
    private final String adminPassword;

    public SecurityConfig(@Value("${admin.username}") String adminUsername,
                          @Value("${admin.password}") String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin.html", "/api/admin/**").authenticated()
                        .anyRequest().permitAll())
                // The only state-changing public endpoint is the contact form, which is
                // deliberately unauthenticated; the admin endpoints are read-only.
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> {});
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password(encoder.encode(adminPassword))
                        .roles("ADMIN")
                        .build());
    }
}
