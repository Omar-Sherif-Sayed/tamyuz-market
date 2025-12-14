package sa.tamyuz.market.business.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sa.tamyuz.market.business.user.constant.ConstantPermission;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(ConstantPermission.adminPermissions),

    PREMIUM_USER(ConstantPermission.premiumUserPermissions),

    USER(ConstantPermission.userPermissions);

    private final Set<Permission> permissions;

}
