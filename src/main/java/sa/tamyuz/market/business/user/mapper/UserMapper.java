package sa.tamyuz.market.business.user.mapper;

import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.schema.response.DtoUser;

public class UserMapper {

    private UserMapper() {
    }

    public static DtoUser toDto(User user) {
        return DtoUser
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
