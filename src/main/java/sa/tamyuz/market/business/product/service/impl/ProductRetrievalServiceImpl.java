package sa.tamyuz.market.business.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.mapper.ProductMapper;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.product.schema.request.ReqProductSearch;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductRetrievalService;
import sa.tamyuz.market.business.product.service.specs.ProductSpecs;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRetrievalServiceImpl implements ProductRetrievalService {

    private final ProductRepository productRepository;

    @Override
    public Page<DtoProduct> search(ReqProductSearch reqProductSearch, Pageable pageable) {

        List<Specification<Product>> specifications = new LinkedList<>();

        Specification<Product> spec =
                ProductSpecs
                        .withName(reqProductSearch.getName())
                        .and(ProductSpecs.withName(reqProductSearch.getName()))
                        .and(ProductSpecs.withMinPrice(reqProductSearch.getMinPrice()))
                        .and(ProductSpecs.withMaxPrice(reqProductSearch.getMaxPrice()))
                        .and(ProductSpecs.withAvailable(reqProductSearch.isAvailable()))
                        .and(ProductSpecs.idDeleted(false));

        specifications.add(spec);

        Specification<Product> allSpecs = Specification.allOf(specifications);
        return
                productRepository
                        .findAll(allSpecs, pageable)
                        .map(ProductMapper::toDto);
    }

}
