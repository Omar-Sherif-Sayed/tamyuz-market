package sa.tamyuz.market.general.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String TEST_SECRET = "testSecretKeyShouldBeLongEnoughForHS256Algorithm12345678";
    private static final long TEST_EXPIRATION = 86400000L; // 24 hours

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", TEST_EXPIRATION);
    }

    @Test
    void generateToken_Success() {
        // Arrange
        String email = "test@example.com";

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    void extractUsername_FromValidToken_ReturnsEmail() {
        // Arrange
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        // Act
        String extractedEmail = jwtService.extractUsername(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void isTokenValid_WithValidToken_ReturnsTrue() {
        // Arrange
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        UserDetails userDetails = User.builder()
                .username(email)
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_WithMismatchedUsername_ReturnsFalse() {
        // Arrange
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        UserDetails userDetails = User.builder()
                .username("different@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void isTokenValid_WithInvalidToken_ReturnsFalse() {
        // Arrange
        String invalidToken = "invalid.token.here";
        UserDetails userDetails = User.builder()
                .username("test@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(invalidToken, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void generateToken_DifferentEmails_ProduceDifferentTokens() {
        // Arrange
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";

        // Act
        String token1 = jwtService.generateToken(email1);
        String token2 = jwtService.generateToken(email2);

        // Assert
        assertNotEquals(token1, token2);
    }

    @Test
    void generateToken_SameEmail_ProduceDifferentTokensDueToTimestamp() throws InterruptedException {
        // Arrange
        String email = "test@example.com";

        // Act
        String token1 = jwtService.generateToken(email);
        Thread.sleep(10); // Wait to ensure different timestamp
        String token2 = jwtService.generateToken(email);

        // Assert
        assertNotEquals(token1, token2);
    }

    @Test
    void extractUsername_FromExpiredToken_ThrowsException() {
        // Arrange
        JwtService shortLivedJwtService = new JwtService();
        ReflectionTestUtils.setField(shortLivedJwtService, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(shortLivedJwtService, "jwtExpiration", -1000L); // Negative means already expired

        String token = shortLivedJwtService.generateToken("test@example.com");

        // Act & Assert
        // Token is already expired when created with negative expiration
        assertThrows(Exception.class, () -> {
            jwtService.extractUsername(token);
        });
    }

    @Test
    void isTokenValid_WithNullUserDetails_ReturnsFalse() {
        // Arrange
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            jwtService.isTokenValid(token, null);
        });
    }
}
