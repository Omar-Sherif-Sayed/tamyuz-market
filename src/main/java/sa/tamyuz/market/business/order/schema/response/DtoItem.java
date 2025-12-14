package sa.tamyuz.market.business.order.schema.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.user.schema.response.DtoUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoItem {

    private Long id;

    private DtoProduct product;

    private Long quantity;

    private BigDecimal unitPrice;

    private Long discountApplied;

    private BigDecimal totalPrice;

    private DtoUser user;

    private Long orderId;

    @CreationTimestamp
    private LocalDateTime timestamps;

}
