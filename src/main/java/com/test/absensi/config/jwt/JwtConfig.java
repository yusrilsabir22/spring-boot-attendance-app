package com.test.absensi.config.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.security.jwt")
@Configuration
@Data
public class JwtConfig {
    private String secretKey;
    private Integer expiration;
    private Integer refreshTokenExpiration;
}
