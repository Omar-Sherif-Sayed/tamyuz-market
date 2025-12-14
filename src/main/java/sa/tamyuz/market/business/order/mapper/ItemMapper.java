package sa.tamyuz.market.business.order.mapper;

import sa.tamyuz.market.business.order.entity.Item;
import sa.tamyuz.market.business.order.schema.response.DtoItem;
import sa.tamyuz.market.business.product.mapper.ProductMapper;
import sa.tamyuz.market.business.user.mapper.UserMapper;

public class ItemMapper {

    private ItemMapper() {
    }

    public static DtoItem toDto(Item item) {
        if (item == null) {
            return null;
        }

        return DtoItem
                .builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .product(ProductMapper.toDto(item.getProduct()))
                .unitPrice(item.getUnitPrice())
                .discountApplied(item.getDiscountApplied())
                .totalPrice(item.getTotalPrice())
                .user(UserMapper.toDto(item.getUser()))
                .orderId(item.getOrder() != null ? item.getOrder().getId() : null)
                .timestamps(item.getTimestamps())
                .build();
    }

}
