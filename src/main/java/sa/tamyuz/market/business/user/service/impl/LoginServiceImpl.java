package sa.tamyuz.market.business.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.user.mapper.UserMapper;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.business.user.schema.request.ReqUserLogin;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.LoginService;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.service.JwtService;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public DtoUserAuth login(ReqUserLogin request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user =
                userRepository
                        .findByEmailAndDeletedFalse(request.getEmail())
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        var jwtToken = jwtService.generateToken(user.getEmail());

        return DtoUserAuth
                .builder()
                .token(jwtToken)
                .user(UserMapper.toDto(user))
                .build();
    }

}
