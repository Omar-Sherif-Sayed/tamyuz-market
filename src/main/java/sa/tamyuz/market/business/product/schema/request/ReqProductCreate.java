package sa.tamyuz.market.business.product.schema.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReqProductCreate {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private Long quantity;

}
