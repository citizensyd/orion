package com.openclassroom.orion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class EnvironmentConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        // En développement local, utiliser dev.env
        String env = System.getenv("ENV");
        if (env == null || env.equals("dev")) {
            configurer.setLocation(new FileSystemResource("dev.env"));
        }

        // En production, les variables seront injectées par Docker Swarm
        return configurer;
    }
}
