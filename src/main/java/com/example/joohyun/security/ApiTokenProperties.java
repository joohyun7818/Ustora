package com.example.joohyun.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.auth")
public class ApiTokenProperties {
    /** 고정 토큰 값 */
    private String token = "change-me";

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
