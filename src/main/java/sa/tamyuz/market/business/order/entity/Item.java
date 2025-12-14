package sa.tamyuz.market.business.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import sa.tamyuz.market.business.order.enums.ItemStatus;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item", uniqueConstraints = {
        @UniqueConstraint(name = "pk_item_id", columnNames = {"id"})})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product_id",
            foreignKey = @ForeignKey(name = "fk_item_product_id",
                    foreignKeyDefinition = "FOREIGN KEY (fk_product_id) REFERENCES product (id)"))
    private Product product;

    private Long quantity;

    private BigDecimal unitPrice;

    private Long discountApplied;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id",
            foreignKey = @ForeignKey(name = "fk_item_user_id",
                    foreignKeyDefinition = "FOREIGN KEY (fk_user_id) REFERENCES user (id)"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order_id",
            foreignKey = @ForeignKey(name = "fk_item_order_id",
                    foreignKeyDefinition = "FOREIGN KEY (fk_order_id) REFERENCES order (id)"))
    private Order order;

    @CreationTimestamp
    private LocalDateTime timestamps;

}
