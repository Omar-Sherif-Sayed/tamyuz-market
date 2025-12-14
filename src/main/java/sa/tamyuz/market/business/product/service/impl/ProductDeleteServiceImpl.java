package sa.tamyuz.market.business.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.product.service.ProductDeleteService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private final ProductRepository productRepository;

    @Override
    public Boolean delete(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDelete(true);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }

    }

}
