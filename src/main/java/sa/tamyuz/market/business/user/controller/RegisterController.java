package sa.tamyuz.market.business.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.business.user.schema.request.ReqUserRegister;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.RegisterService;
import sa.tamyuz.market.general.schema.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth/register")
@Tag(name = "Registration", description = "User registration endpoints")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns authentication token with user details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DtoUserAuth.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Invalid registration data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponse<DtoUserAuth>> register(@RequestBody ReqUserRegister reqUserRegister) {
        return ResponseEntity.ok(new BaseResponse<>(registerService.register(reqUserRegister)));
    }

}
