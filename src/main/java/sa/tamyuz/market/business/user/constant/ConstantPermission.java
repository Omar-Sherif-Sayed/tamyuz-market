package sa.tamyuz.market.business.user.constant;

import sa.tamyuz.market.business.user.enums.Permission;

import java.util.Set;

public class ConstantPermission {

    private ConstantPermission() {
    }

    public static final Set<Permission> adminPermissions =
            Set.of(
                    Permission.CREATE_PRODUCT,
                    Permission.UPDATE_PRODUCT,
                    Permission.RETRIEVE_PRODUCT,
                    Permission.DELETE_PRODUCT
            );

    public static final Set<Permission> userPermissions =
            Set.of(
                    Permission.CREATE_ORDER,
                    Permission.CREATE_PRODUCT,

                    Permission.UPDATE_ITEM,
                    Permission.UPDATE_PRODUCT,

                    Permission.RETRIEVE_PRODUCT
            );

    public static final Set<Permission> premiumUserPermissions =
            Set.of(
                    Permission.CREATE_ORDER,
                    Permission.CREATE_PRODUCT,

                    Permission.UPDATE_ITEM,
                    Permission.UPDATE_PRODUCT,

                    Permission.RETRIEVE_PRODUCT
            );

}
