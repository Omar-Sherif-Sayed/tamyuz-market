package sa.tamyuz.market.business.product.service.specs;

import org.springframework.data.jpa.domain.Specification;
import sa.tamyuz.market.business.product.entity.Product;

import java.math.BigDecimal;

public class ProductSpecs {

    private ProductSpecs() {
    }

    public static Specification<Product> withName(String name) {
        return (root, query, cb) ->
                name == null || name.trim().isEmpty()
                        ? cb.isTrue(cb.literal(true))
                        : cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> withMinPrice(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null
                        ? cb.isTrue(cb.literal(true))
                        : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> withMaxPrice(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null
                        ? cb.isTrue(cb.literal(true))
                        : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> withAvailable(boolean available) {
        return (root, query, cb) ->
                available
                        ? cb.greaterThan(root.get("quantity"), 0)
                        : cb.equal(root.get("quantity"), 0);
    }

    public static Specification<Product> idDeleted(boolean deleted) {
        return (root, query, cb) -> cb.equal(root.get("delete"), deleted);
    }

}
