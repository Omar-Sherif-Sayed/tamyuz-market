package sa.tamyuz.market.business.user.entity;

import org.junit.jupiter.api.Test;
import sa.tamyuz.market.business.user.enums.Role;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilder() {
        // Arrange & Act
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .deleted(false)
                .timestamps(LocalDateTime.now())
                .build();

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertFalse(user.isDeleted());
        assertNotNull(user.getTimestamps());
    }

    @Test
    void testUserSettersAndGetters() {
        // Arrange
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // Act
        user.setId(2L);
        user.setEmail("user@test.com");
        user.setPassword("pass123");
        user.setRole(Role.ADMIN);
        user.setDeleted(true);
        user.setTimestamps(now);

        // Assert
        assertEquals(2L, user.getId());
        assertEquals("user@test.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertTrue(user.isDeleted());
        assertEquals(now, user.getTimestamps());
    }

    @Test
    void testUserNoArgsConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getEmail());
    }

    @Test
    void testUserAllArgsConstructor() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        User user = new User(
                1L,
                "test@example.com",
                "password",
                Role.USER,
                false,
                timestamp
        );

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertFalse(user.isDeleted());
        assertEquals(timestamp, user.getTimestamps());
    }
}
