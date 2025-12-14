package sa.tamyuz.market.business.product.schema.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReqProductSearch {

    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private boolean available;

}
