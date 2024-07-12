package com.hklim.finingserver.global.config;

import com.hklim.finingserver.global.security.jwt.CustomAccessDeniedHandler;
import com.hklim.finingserver.global.security.jwt.CustomAuthenticationEntryPoint;
import com.hklim.finingserver.global.security.jwt.CustomUserDetailsService;
import com.hklim.finingserver.global.security.jwt.JwtAuthFilter;
import com.hklim.finingserver.global.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/api/vi/member/**", "/api/v1/auth/**", "/api/v1/stock/**", "/api/v1/ui/main/**", "/api/v1/ui/stock/**"
    };
    private static final String[] AUTH_ADMIN = {
            "/api/v1/stock/scrap/**", "/api/v1/indicator/scrap/**", "/api/v1/auth/logout"
    };

    private static final String[] AUTH_USER = {
            "/api/v1/auth/logout", "/api/v1/portfolio/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF, CORS
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.formLogin((form) -> form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling((exceptionHandler) -> exceptionHandler
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(AUTH_ADMIN).hasRole("ADMIN")
                .requestMatchers(AUTH_USER).hasRole("USER")
                .anyRequest().authenticated());

        return http.build();
    }

}
