package sa.tamyuz.market.business.order.mapper;

import sa.tamyuz.market.business.order.entity.Order;
import sa.tamyuz.market.business.order.schema.response.DtoOrder;
import sa.tamyuz.market.business.user.mapper.UserMapper;

public class OrderMapper {

    private OrderMapper() {
    }

    public static DtoOrder toDto(Order order) {
        return DtoOrder
                .builder()
                .id(order.getId())
                .user(UserMapper.toDto(order.getUser()))
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .timestamps(order.getTimestamps())
                .build();
    }

}
