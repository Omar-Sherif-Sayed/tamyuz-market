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
import sa.tamyuz.market.business.user.schema.request.ReqUserRegister;
import sa.tamyuz.market.business.user.schema.response.DtoUser;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.RegisterService;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private RegisterService registerService;

    @Test
    void register_Success() throws Exception {
        // Arrange
        ReqUserRegister request = new ReqUserRegister();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER);

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("jwt-token")
                .user(dtoUser)
                .build();

        when(registerService.register(any(ReqUserRegister.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value("jwt-token"))
                .andExpect(jsonPath("$.data.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.user.role").value("USER"));
    }

    @Test
    void register_UserAlreadyExists_ReturnsBadRequest() throws Exception {
        // Arrange
        ReqUserRegister request = new ReqUserRegister();
        request.setEmail("existing@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER);

        when(registerService.register(any(ReqUserRegister.class)))
                .thenThrow(new BaseException(ErrorCode.EXIST_USER));

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_AdminRole_Success() throws Exception {
        // Arrange
        ReqUserRegister request = new ReqUserRegister();
        request.setEmail("admin@example.com");
        request.setPassword("password123");
        request.setRole(Role.ADMIN);

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("admin@example.com")
                .role(Role.ADMIN)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("jwt-token")
                .user(dtoUser)
                .build();

        when(registerService.register(any(ReqUserRegister.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.role").value("ADMIN"));
    }

    @Test
    void register_ReturnsBaseResponseStructure() throws Exception {
        // Arrange
        ReqUserRegister request = new ReqUserRegister();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER);

        DtoUser dtoUser = DtoUser.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .build();

        DtoUserAuth response = DtoUserAuth.builder()
                .token("jwt-token")
                .user(dtoUser)
                .build();

        when(registerService.register(any(ReqUserRegister.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }
}
