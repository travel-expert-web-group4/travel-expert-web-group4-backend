package org.group4.travelexpertsapi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("uploads/images/agents");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/images/agents/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
