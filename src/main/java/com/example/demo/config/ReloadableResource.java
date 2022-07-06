package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ReloadableResource extends ReloadableResourceBundleMessageSource {
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("/resources/static/");  // name of the resource bundle
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(10);

        return source;
    }
}
