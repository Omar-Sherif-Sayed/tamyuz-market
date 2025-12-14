package sa.tamyuz.market.business.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sa.tamyuz.market.business.order.entity.Item;
import sa.tamyuz.market.business.order.enums.ItemStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByProductIdAndUserIdAndStatus(Long productId, Long userId, ItemStatus status);

    List<Item> findALlByUserIdAndStatus(Long userId, ItemStatus status);

}
