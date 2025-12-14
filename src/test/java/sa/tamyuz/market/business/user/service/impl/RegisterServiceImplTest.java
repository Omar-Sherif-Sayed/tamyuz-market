package sa.tamyuz.market.business.user.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.business.user.schema.request.ReqUserRegister;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.service.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterServiceImpl registerService;

    private ReqUserRegister request;
    private User user;

    @BeforeEach
    void setUp() {
        request = new ReqUserRegister();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER);

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void register_Success() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

        // Act
        DtoUserAuth result = registerService.register(request);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        assertNotNull(result.getUser());
        assertEquals("test@example.com", result.getUser().getEmail());

        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken("test@example.com");
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> {
            registerService.register(request);
        });

        assertEquals(ErrorCode.EXIST_USER, exception.getErrorCode());
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void register_PasswordIsEncoded() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

        // Act
        registerService.register(request);

        // Assert
        verify(passwordEncoder).encode("password123");
    }

    @Test
    void register_AdminRole_Success() {
        // Arrange
        request.setRole(Role.ADMIN);
        User adminUser = User.builder()
                .id(2L)
                .email("admin@example.com")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(adminUser);
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

        // Act
        DtoUserAuth result = registerService.register(request);

        // Assert
        assertNotNull(result);
        assertEquals(Role.ADMIN, result.getUser().getRole());
    }
}
