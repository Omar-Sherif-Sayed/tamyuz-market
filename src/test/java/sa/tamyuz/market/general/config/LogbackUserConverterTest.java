package sa.tamyuz.market.general.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogbackUserConverterTest {

    private LogbackUserConverter converter;

    @Mock
    private ILoggingEvent event;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        converter = new LogbackUserConverter();
    }

    @Test
    void convert_WithAuthenticatedUser_ReturnsUsername() {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            String result = converter.convert(event);

            // Assert
            assertEquals("test@example.com", result);
        }
    }

    @Test
    void convert_WithNoAuthentication_ReturnsAnonymous() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            String result = converter.convert(event);

            // Assert
            assertEquals("anonymous", result);
        }
    }

    @Test
    void convert_WithNullSecurityContext_ReturnsAnonymous() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(null);

            // Act
            String result = converter.convert(event);

            // Assert
            assertEquals("anonymous", result);
        }
    }

    @Test
    void convert_WithException_ReturnsAnonymous() {
        // Arrange
        when(securityContext.getAuthentication()).thenThrow(new RuntimeException("Test exception"));

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act
            String result = converter.convert(event);

            // Assert
            assertEquals("anonymous", result);
        }
    }
}
