package sa.tamyuz.market.general.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import sa.tamyuz.market.general.enums.Language;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.of(Language.AR.getCode()));
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
