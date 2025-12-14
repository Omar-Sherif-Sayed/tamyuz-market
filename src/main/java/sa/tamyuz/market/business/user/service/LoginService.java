package sa.tamyuz.market.business.user.service;

import sa.tamyuz.market.business.user.schema.request.ReqUserLogin;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;

public interface LoginService {

    DtoUserAuth login(ReqUserLogin request);

}
