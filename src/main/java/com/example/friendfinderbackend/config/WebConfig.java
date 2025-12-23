package com.example.friendfinderbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path rootPath = Paths.get(System.getProperty("user.dir"));

        Path absoluteUploadPath = rootPath.resolve("uploads");


        String resourceLocation = "file:" + absoluteUploadPath.toUri().getPath();


        if (!resourceLocation.endsWith("/")) {
            resourceLocation += "/";
        }

        System.out.println("Serving static files from: " + resourceLocation); // للمراجعة في Console

        registry.addResourceHandler("/uploads/**")

                .addResourceLocations(resourceLocation);
    }

}
