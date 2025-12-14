package sa.tamyuz.market.business.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import sa.tamyuz.market.business.user.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "pk_users_id", columnNames = {"id"}),
        @UniqueConstraint(name = "uk_email", columnNames = {"email", "deleted"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @CreationTimestamp
    private LocalDateTime timestamps;

}
