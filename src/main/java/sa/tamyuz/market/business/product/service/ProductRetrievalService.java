package sa.tamyuz.market.business.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sa.tamyuz.market.business.product.schema.request.ReqProductSearch;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

public interface ProductRetrievalService {

    Page<DtoProduct> search(ReqProductSearch reqProductSearch, Pageable pageable);

}
