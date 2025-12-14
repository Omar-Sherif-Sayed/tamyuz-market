package sa.tamyuz.market.business.user.service;

import sa.tamyuz.market.business.user.schema.request.ReqUserRegister;
import sa.tamyuz.market.business.user.schema.response.DtoUserAuth;

public interface RegisterService {

    DtoUserAuth register(ReqUserRegister reqUserRegister);

}
