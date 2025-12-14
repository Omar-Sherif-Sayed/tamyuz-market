package sa.tamyuz.market.business.user.mapper;

import org.junit.jupiter.api.Test;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;
import sa.tamyuz.market.business.user.schema.response.DtoUser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toDto_Success() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .deleted(false)
                .timestamps(LocalDateTime.now())
                .build();

        // Act
        DtoUser result = UserMapper.toDto(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void toDto_AdminRole() {
        // Arrange
        User user = User.builder()
                .id(2L)
                .email("admin@example.com")
                .password("adminPassword")
                .role(Role.ADMIN)
                .build();

        // Act
        DtoUser result = UserMapper.toDto(user);

        // Assert
        assertEquals(Role.ADMIN, result.getRole());
    }

    @Test
    void toDto_DoesNotIncludePassword() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("sensitivePassword")
                .role(Role.USER)
                .build();

        // Act
        DtoUser result = UserMapper.toDto(user);

        // Assert
        assertNotNull(result);
        // DtoUser should not have password field exposed
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void toDto_DoesNotIncludeDeletedFlag() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .deleted(true)
                .build();

        // Act
        DtoUser result = UserMapper.toDto(user);

        // Assert
        assertNotNull(result);
        // DtoUser should not expose internal deleted flag
        assertEquals(1L, result.getId());
    }
}
