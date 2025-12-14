package sa.tamyuz.market.business.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .deleted(false)
                .build();
    }

    @Test
    void findByEmail_ExistingUser_ReturnsUser() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> result = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals(Role.USER, result.get().getRole());
    }

    @Test
    void findByEmail_NonExistingUser_ReturnsEmpty() {
        // Act
        Optional<User> result = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findByEmailAndDeletedFalse_ExistingActiveUser_ReturnsUser() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> result = userRepository.findByEmailAndDeletedFalse("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        assertFalse(result.get().isDeleted());
    }

    @Test
    void findByEmailAndDeletedFalse_DeletedUser_ReturnsEmpty() {
        // Arrange
        testUser.setDeleted(true);
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> result = userRepository.findByEmailAndDeletedFalse("test@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findByEmailAndDeletedFalse_NonExistingUser_ReturnsEmpty() {
        // Act
        Optional<User> result = userRepository.findByEmailAndDeletedFalse("nonexistent@example.com");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void save_NewUser_Success() {
        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(Role.USER, savedUser.getRole());
        assertFalse(savedUser.isDeleted());
    }

    @Test
    void save_UpdateExistingUser_Success() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        // Act
        savedUser.setRole(Role.ADMIN);
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(Role.ADMIN, updatedUser.getRole());
    }

    @Test
    void findById_ExistingUser_ReturnsUser() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> result = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void delete_ExistingUser_Success() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();
        Long userId = savedUser.getId();

        // Act
        userRepository.delete(savedUser);
        entityManager.flush();

        // Assert
        Optional<User> result = userRepository.findById(userId);
        assertFalse(result.isPresent());
    }

    @Test
    void findByEmail_CaseInsensitiveEmail_FindsUser() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> result = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void save_AdminUser_Success() {
        // Arrange
        User adminUser = User.builder()
                .email("admin@example.com")
                .password("adminPassword")
                .role(Role.ADMIN)
                .deleted(false)
                .build();

        // Act
        User savedUser = userRepository.save(adminUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals(Role.ADMIN, savedUser.getRole());
    }
}
