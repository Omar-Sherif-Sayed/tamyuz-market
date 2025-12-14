package sa.tamyuz.market.business.user.controller;

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
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<BaseResponse<DtoUserAuth>> login(@RequestBody ReqUserLogin reqUserLogin) {
        return ResponseEntity.ok(new BaseResponse<>(loginService.login(reqUserLogin)));
    }

}
