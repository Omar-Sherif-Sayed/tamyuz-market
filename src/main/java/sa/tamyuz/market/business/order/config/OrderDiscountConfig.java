package sa.tamyuz.market.business.order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "order-discount")
public class OrderDiscountConfig {

    private Long user;

    private Long premiumUser;

    private Long above500;

}
