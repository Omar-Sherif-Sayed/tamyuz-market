package sa.tamyuz.market.business.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(name = "pk_orders_id", columnNames = {"id"})
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id",
            foreignKey = @ForeignKey(name = "fk_orders_user_id",
                    foreignKeyDefinition = "FOREIGN KEY (fk_user_id) REFERENCES user (id)"))
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long totalDiscountApplied;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order")
    private List<Item> items;

    @CreationTimestamp
    private LocalDateTime timestamps;

}
