package sa.tamyuz.market.business.order.schema.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.user.schema.response.DtoUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrder {

    private Long id;

    private DtoUser user;

    private OrderStatus status;

    private BigDecimal totalPrice;

    private LocalDateTime timestamps;

}
