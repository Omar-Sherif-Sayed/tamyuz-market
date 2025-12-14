package sa.tamyuz.market.general.security.auth;


import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        var user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> BaseException.builder().errorCode(ErrorCode.NOT_FOUND_USER).build());

        if (user.isDeleted()) {
            throw new BaseException(ErrorCode.AUTH_USER_DISABLED);
        }

        return new ApplicationUser(user);
    }

}
