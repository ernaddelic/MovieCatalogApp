package com.example.movieratingsservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.secret")
public class JwtProps {
    
    private String key = "secret";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}