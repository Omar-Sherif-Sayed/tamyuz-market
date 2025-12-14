package sa.tamyuz.market.business.product.service;

import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

public interface ProductCreateService {

    DtoProduct create(ReqProductCreate reqProductCreate);

}
