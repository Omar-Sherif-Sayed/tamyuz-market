package sa.tamyuz.market.general.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public String findSubject() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
