package com.test.absensi.config.security;

import com.test.absensi.config.jwt.JwtAuthenticationFilter;
import com.test.absensi.config.security.AuthEntryPoint;
import com.test.absensi.user.Permission;
import com.test.absensi.user.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final AuthEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/public/**")
                        .permitAll()

                        .requestMatchers("/api/pegawai/**").hasAnyRole(Profile.ADMIN.name(), Profile.MANAGER.name(), Profile.USER.name())
                        .requestMatchers("/api/presensi/**").hasAnyRole(Profile.ADMIN.name(), Profile.MANAGER.name(), Profile.USER.name())

                        .requestMatchers(HttpMethod.GET, "/api/pegawai/admin-**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/pegawai/admin-**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())

                        .requestMatchers(HttpMethod.POST, "/api/pegawai/ubah-**").hasAnyAuthority(Permission.USER_UPDATE.name())

                        .requestMatchers(HttpMethod.GET, "/api/presensi/in").hasAnyAuthority(Permission.USER_UPDATE.name(), Permission.MANAGER_UPDATE.name())
                        .requestMatchers(HttpMethod.GET, "/api/presensi/out").hasAnyAuthority(Permission.USER_UPDATE.name(), Permission.MANAGER_UPDATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/presensi/absensi").hasAnyAuthority(Permission.USER_CREATE .name(), Permission.MANAGER_CREATE.name())
                        .requestMatchers(HttpMethod.GET, "/api/presensi/datar/pegawai").hasAnyAuthority(Permission.USER_READ.name(), Permission.MANAGER_READ.name())

                        .requestMatchers(HttpMethod.GET, "/api/presensi/datar/admin").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name())

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
