package sa.tamyuz.market.general.security.util;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static final String[] antMatchers = {
            // Swagger
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/",
            "/swagger-ui/**",

            // H2
            "/h2-console/**",

            // Authentication
            "/v1/auth/login",
            "/v1/auth/login/**",
            "/v1/auth/register",
            "/v1/auth/register/**",

            // Test
            "/v1/test/**"

    };

}
