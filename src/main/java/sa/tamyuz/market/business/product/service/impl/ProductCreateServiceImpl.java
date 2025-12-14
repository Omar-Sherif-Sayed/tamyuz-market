package sa.tamyuz.market.business.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.mapper.ProductMapper;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductCreateService;

@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {

    private final ProductRepository productRepository;

    @Override
    public DtoProduct create(ReqProductCreate reqProductCreate) {

        Product product = ProductMapper.toEntity(reqProductCreate);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

}
