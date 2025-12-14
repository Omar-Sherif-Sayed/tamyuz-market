package sa.tamyuz.market.business.user.schema.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserAuth {

    private String token;
    private DtoUser user;


}
