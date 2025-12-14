package sa.tamyuz.market.general.config.i18n;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import sa.tamyuz.market.general.enums.Language;

import java.util.Locale;

@Configuration
public class LocalizationConfig {

    @Bean
    public LocaleResolver customLocaleResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.of(Language.AR.getCode()));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

}
