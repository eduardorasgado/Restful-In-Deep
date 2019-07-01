package com.eduardocode.webservices.rest.restfulindeep.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * <h1>ApplicationConfig</h1>
 * Class that set important and general beans to application
 *
 * @author Eduardo Rasgado Ruiz
 * @version 1.0
 */
@Configuration
public class ApplicationConfig {

    /**
     * Bean that set to {@link CustomLocaleResolver} as default local resolver
     * for every time LocaleContextHolder is called in all the application resources.
     * This will set correct locale language given the request key: Accept-Language
     * passed to request in client side.
     * @return
     *      A configuration class to solve application locale
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver();
    }

    /**
     * Method that tells our application where is the source of our translated messages for internationalization,
     * it should be in resources/ as root.
     * @return
     *      message source bundle with some mensajes_* properties
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        // This will take messages and everithing in resources/ that start with mensajes_*
        messageSource.setBasename("mensajes");
        messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
}
