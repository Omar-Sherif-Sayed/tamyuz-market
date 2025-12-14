package sa.tamyuz.market.business.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.mapper.UserMapper;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.business.user.schema.request.ReqUserRegister;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;
import sa.tamyuz.market.business.user.service.RegisterService;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.service.JwtService;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DtoUserAuth register(ReqUserRegister request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.EXIST_USER);
        }

        // Create new user
        var user =
                User
                        .builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .build();

        userRepository.save(user);

        // Generate JWT token
        var jwtToken = jwtService.generateToken(request.getEmail());

        return DtoUserAuth
                .builder()
                .token(jwtToken)
                .user(UserMapper.toDto(user))
                .build();
    }

}
