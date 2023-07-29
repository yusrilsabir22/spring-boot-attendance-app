package com.test.absensi.config;

import com.test.absensi.models.Permission;
import com.test.absensi.models.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        .requestMatchers("/api/pegawai/**").hasAnyRole(Profile.ADMIN.name(), Profile.MANAGER.name(), Profile.USER.name())

                        .requestMatchers(HttpMethod.GET, "/api/pegawai/admin-**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/pegawai/admin-**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())

                        .requestMatchers(HttpMethod.POST, "/api/pegawai/ubah-**").hasAnyAuthority(Permission.USER_UPDATE.name())

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(httpException -> httpException
                        .authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpException -> httpException
                        .configure(http));

        return http.build();
    }

}
