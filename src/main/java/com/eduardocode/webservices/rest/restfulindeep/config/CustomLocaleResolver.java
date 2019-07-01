package com.eduardocode.webservices.rest.restfulindeep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * <h1>CustomLocaleResolver</h1>
 * Configuration class for internationalization feature in whole appllication.
 * This bean is
 * @author Eduardo Rasgado Ruiz
 * @version 1.0
 */
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    @Value("${app.locale.spanish}")
    private String localeEs;
    @Value("${app.local.spanish.mx}")
    private String localeMx;
    @Value("${app.locale.english}")
    private String localeEn;
    @Value("${app.locale.french}")
    private String localeFr;

    private String languageHeaderName = "Accept-Language";
    private List<Locale> LOCALES = null;

    /**
     * Method that overrides the way to get default language messages will handle.
     * It invokes every time locale context holder is called in request
     * @see com.eduardocode.webservices.rest.restfulindeep.controller.UserResource
     * @param request
     * @return
     *      local default or one of the LOCALES list of locales available
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if(LOCALES == null) {
            LOCALES = Arrays.asList(
                    new Locale(localeEs),
                    new Locale(localeEs, localeMx),
                    new Locale(localeFr),
                    new Locale(localeEn)
            );
        }

        String headerLanguage = request.getHeader(languageHeaderName);

        Locale.setDefault(Locale.US);

        return headerLanguage == null || headerLanguage.isEmpty() ?
                Locale.getDefault() :
                Locale.lookup(Locale.LanguageRange.parse(headerLanguage), LOCALES);
    }
}
