package com.elektrodevs.elektromart.config;

import com.elektrodevs.elektromart.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class FileServiceConfig {

    @Value("${file.userdata.path}")
    private String filepath;

    @Bean
    public FileService fileService(ResourceLoader resourceLoader) {
        return new FileService(resourceLoader, filepath);
    }
}
