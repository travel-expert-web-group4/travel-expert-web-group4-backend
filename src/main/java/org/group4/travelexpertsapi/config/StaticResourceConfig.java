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

        String agentPath = getuploadPath("uploads/images/agents");
        String customerPath = getuploadPath("uploads/images/customers");

        registry.addResourceHandler("/images/agents/**")
                .addResourceLocations("file:" + agentPath + "/");

        registry.addResourceHandler("/images/customers/**")
                .addResourceLocations("file:" + customerPath + "/");


    }

    public String getuploadPath(String pathname) {
        Path uploadDir = Paths.get(pathname);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        return uploadPath;
    }
}
