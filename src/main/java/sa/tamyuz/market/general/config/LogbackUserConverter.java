package sa.tamyuz.market.general.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LogbackUserConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "NO_USER";
    }

}
