package sa.tamyuz.market.business.user.controller;


import lombok.RequiredArgsConstructor;
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
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<BaseResponse<DtoUserAuth>> register(@RequestBody ReqUserRegister reqUserRegister) {
        return ResponseEntity.ok(new BaseResponse<>(registerService.register(reqUserRegister)));
    }

}
