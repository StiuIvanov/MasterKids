package com.example.demo.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    private final CloudinaryConfig config;

    public AppConfig(CloudinaryConfig config) {
        this.config = config;
    }

    @Bean
    public Cloudinary cloudinary() {
        final Cloudinary cloudinary = new Cloudinary(
                Map.of(
                        "cloud_name", config.getCloudName(),
                        "api_key", config.getApiKey(),
                        "api_secret", config.getApiSecret()));

        System.out.println();
        return cloudinary;
    }

}
