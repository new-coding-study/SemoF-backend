package com.loung.semof.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.add-resource-locations}")
    private String imageResourceLocations;

    @Value("${image.add-resource-handler}**")
    private String imageResourceHandler;

    @Value("${file.add-resource-locations}")
    private String fileResourceLocations;

    @Value("${file.add-resource-handler}")
    private String fileResourceHandler;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(fileResourceHandler)
                .addResourceLocations("file:" + fileResourceLocations);

        registry.addResourceHandler(imageResourceHandler)
                .addResourceLocations("file:" + imageResourceLocations);

    }
}
