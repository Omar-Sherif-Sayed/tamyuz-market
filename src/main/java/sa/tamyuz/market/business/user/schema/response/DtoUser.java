package sa.tamyuz.market.business.user.schema.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sa.tamyuz.market.business.user.enums.Role;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

    private Long id;
    private String email;
    private Role role;

}
