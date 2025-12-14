package sa.tamyuz.market.business.product.service;

import sa.tamyuz.market.business.product.schema.request.ReqProductUpdate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

public interface ProductUpdateService {

    DtoProduct update(ReqProductUpdate reqProductUpdate);

}
