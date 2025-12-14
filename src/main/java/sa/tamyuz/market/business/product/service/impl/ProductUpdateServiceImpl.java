package sa.tamyuz.market.business.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.mapper.ProductMapper;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.product.schema.request.ReqProductUpdate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductUpdateService;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;


@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductRepository productRepository;

    @Override
    public DtoProduct update(ReqProductUpdate reqProductUpdate) {

        Product productEntity =
                productRepository
                        .findById(reqProductUpdate.getId())
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_PRODUCT));

        Product productMapped = ProductMapper.toEntity(productEntity, reqProductUpdate);

        Product savedProduct = productRepository.save(productMapped);

        return ProductMapper.toDto(savedProduct);
    }

}
