package com.eduardocode.webservices.rest.restfulindeep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for internationalization feature in whole appllication.
 */
@Configuration
public class InternationalizationBasicConfiguration {

    /**
     * Bean that set a default language for not specific requests based on header key: Accept-language
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }


    /**
     * Method to tell our application where is the source of our translated messages for internationalization,
     * it should be in resources/ as root.
     * @return
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        // This will take messages and everithing in resources/ that start with messages*
        messageSource.setBasename("mensajes");

        return messageSource;
    }
}
