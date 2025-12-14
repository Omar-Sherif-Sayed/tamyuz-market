package sa.tamyuz.market.business.product.mapper;

import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.request.ReqProductUpdate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

public class ProductMapper {

    private ProductMapper() {
    }

    public static DtoProduct toDto(Product product) {
        return DtoProduct
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .timestamps(product.getTimestamps())
                .delete(product.getDelete())
                .build();
    }

    public static Product toEntity(ReqProductCreate reqProductCreate) {
        return Product
                .builder()
                .name(reqProductCreate.getName())
                .description(reqProductCreate.getDescription())
                .price(reqProductCreate.getPrice())
                .quantity(reqProductCreate.getQuantity())
                .delete(false)
                .build();
    }

    public static Product toEntity(Product product, ReqProductUpdate reqProductUpdate) {

        product.setName(reqProductUpdate.getName());
        product.setDescription(reqProductUpdate.getDescription());
        product.setPrice(reqProductUpdate.getPrice());
        product.setQuantity(reqProductUpdate.getQuantity());
        product.setDelete(reqProductUpdate.isDelete());

        return product;
    }

}
