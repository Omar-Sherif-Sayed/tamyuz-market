package sa.tamyuz.market.business.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.business.user.schema.request.ReqUserLogin;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.LoginService;
import sa.tamyuz.market.general.schema.response.BaseResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth/login")
@Tag(name = "Authentication", description = "User authentication endpoints")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @Operation(
            summary = "User login",
            description = "Authenticates a user with email and password and returns JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DtoUserAuth.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Invalid credentials",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "User not found",
                    content = @Content
            )
    })
    public ResponseEntity<BaseResponse<DtoUserAuth>> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReqUserLogin.class)
                    )
            ) @RequestBody ReqUserLogin reqUserLogin) {
        return ResponseEntity.ok(new BaseResponse<>(loginService.login(reqUserLogin)));
    }

}
