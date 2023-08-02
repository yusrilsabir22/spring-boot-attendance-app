package com.test.absensi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.amazon.s3")
@Configuration
@Data
public class S3Config {
    private String ApiSecret;
    private String apiKey;
    private String bucket;
    private String region;
    private String endpointURL;
}
