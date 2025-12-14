package sa.tamyuz.market.business.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(name = "pk_product_id", columnNames = {"id"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;

    private Boolean delete = false;

    @CreationTimestamp
    private LocalDateTime timestamps;

}
