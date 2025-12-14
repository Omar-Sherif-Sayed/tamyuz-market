package sa.tamyuz.market.general.security.auth;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Permission;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Service
@Transactional
public class ApplicationUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = -3758699237203996390L;

    private transient User user;

    public ApplicationUser() {
    }

    public ApplicationUser(User user) {
        this.user = user;
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Permission> permissions = user.getRole().getPermissions();

        if (permissions != null && !permissions.isEmpty()) {
            permissions.forEach(permission ->
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()))
            );
        }

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public @NonNull String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return !user.isDeleted();
    }

}
