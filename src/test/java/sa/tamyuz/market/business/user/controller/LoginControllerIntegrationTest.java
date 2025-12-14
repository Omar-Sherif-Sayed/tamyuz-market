package sa.tamyuz.market.business.user.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sa.tamyuz.market.business.user.enums.Role;
import sa.tamyuz.market.business.user.schema.request.ReqUserLogin;
import sa.tamyuz.market.business.user.schema.response.DtoUser;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.LoginService;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private LoginService loginService;

    @Test
    void login_Success() throws Exception {
        // Arrange
        ReqUserLogin request = new ReqUserLogin();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("jwt-token")
                .user(dtoUser)
                .build();

        when(loginService.login(any(ReqUserLogin.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value("jwt-token"))
                .andExpect(jsonPath("$.data.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.user.role").value("USER"));
    }

    @Test
    void login_UserNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        ReqUserLogin request = new ReqUserLogin();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password123");

        when(loginService.login(any(ReqUserLogin.class)))
                .thenThrow(new BaseException(ErrorCode.NOT_FOUND_USER));

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void login_AdminUser_Success() throws Exception {
        // Arrange
        ReqUserLogin request = new ReqUserLogin();
        request.setEmail("admin@example.com");
        request.setPassword("password123");

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("admin@example.com")
                .role(Role.ADMIN)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("admin-jwt-token")
                .user(dtoUser)
                .build();

        when(loginService.login(any(ReqUserLogin.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value("admin-jwt-token"))
                .andExpect(jsonPath("$.data.user.role").value("ADMIN"));
    }

    @Test
    void login_ReturnsBaseResponseStructure() throws Exception {
        // Arrange
        ReqUserLogin request = new ReqUserLogin();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("jwt-token")
                .user(dtoUser)
                .build();

        when(loginService.login(any(ReqUserLogin.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }
}
