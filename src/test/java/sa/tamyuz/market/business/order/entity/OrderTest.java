package sa.tamyuz.market.business.order.entity;

import org.junit.jupiter.api.Test;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderBuilder() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .build();

        List<Item> items = new ArrayList<>();

        // Act
        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDING_PAYMENT)
                .totalDiscountApplied(10L)
                .totalPrice(new BigDecimal("500.00"))
                .items(items)
                .timestamps(LocalDateTime.now())
                .build();

        // Assert
        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals(user, order.getUser());
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
        assertEquals(10L, order.getTotalDiscountApplied());
        assertEquals(new BigDecimal("500.00"), order.getTotalPrice());
        assertEquals(items, order.getItems());
        assertNotNull(order.getTimestamps());
    }

    @Test
    void testOrderSettersAndGetters() {
        // Arrange
        Order order = new Order();
        User user = User.builder().id(2L).build();
        LocalDateTime now = LocalDateTime.now();

        // Act
        order.setId(3L);
        order.setUser(user);
        order.setStatus(OrderStatus.COMPLETED);
        order.setTotalDiscountApplied(15L);
        order.setTotalPrice(new BigDecimal("750.50"));
        order.setItems(new ArrayList<>());
        order.setTimestamps(now);

        // Assert
        assertEquals(3L, order.getId());
        assertEquals(user, order.getUser());
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        assertEquals(15L, order.getTotalDiscountApplied());
        assertEquals(new BigDecimal("750.50"), order.getTotalPrice());
        assertNotNull(order.getItems());
        assertEquals(now, order.getTimestamps());
    }

    @Test
    void testOrderNoArgsConstructor() {
        // Act
        Order order = new Order();

        // Assert
        assertNotNull(order);
        assertNull(order.getId());
        assertNull(order.getUser());
    }
}
