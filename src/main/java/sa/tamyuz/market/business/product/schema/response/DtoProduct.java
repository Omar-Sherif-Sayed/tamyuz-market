package sa.tamyuz.market.business.product.schema.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;

    private LocalDateTime timestamps;

    private Boolean delete;

}
