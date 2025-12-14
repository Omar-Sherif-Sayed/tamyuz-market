package sa.tamyuz.market.general.security.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {

    @Test
    void antMatchers_ContainsSwaggerEndpoints() {
        // Assert
        assertTrue(containsPattern("/v3/api-docs"));
        assertTrue(containsPattern("/swagger-ui/**"));
        assertTrue(containsPattern("/swagger-ui.html"));
    }

    @Test
    void antMatchers_ContainsAuthEndpoints() {
        // Assert
        assertTrue(containsPattern("/v1/auth/login"));
        assertTrue(containsPattern("/v1/auth/register"));
    }

    @Test
    void antMatchers_ContainsH2ConsoleEndpoint() {
        // Assert
        assertTrue(containsPattern("/h2-console/**"));
    }

    @Test
    void antMatchers_ContainsTestEndpoint() {
        // Assert
        assertTrue(containsPattern("/v1/test/**"));
    }

    @Test
    void antMatchers_IsNotEmpty() {
        // Assert
        assertNotNull(SecurityUtil.antMatchers);
        assertTrue(SecurityUtil.antMatchers.length > 0);
    }

    @Test
    void antMatchers_HasExpectedCount() {
        // Assert
        // Verify there are at least the major endpoint groups
        assertTrue(SecurityUtil.antMatchers.length >= 10);
    }

    private boolean containsPattern(String pattern) {
        for (String antMatcher : SecurityUtil.antMatchers) {
            if (antMatcher.equals(pattern)) {
                return true;
            }
        }
        return false;
    }
}
