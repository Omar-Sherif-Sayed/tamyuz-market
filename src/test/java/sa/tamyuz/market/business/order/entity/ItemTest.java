package sa.tamyuz.market.business.order.entity;

import org.junit.jupiter.api.Test;
import sa.tamyuz.market.business.order.enums.ItemStatus;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testItemBuilder() {
        // Arrange
        Product product = Product.builder().id(1L).name("Test Product").build();
        User user = User.builder().id(1L).email("test@example.com").build();
        Order order = Order.builder().id(1L).build();

        // Act
        Item item = Item.builder()
                .id(1L)
                .product(product)
                .quantity(5L)
                .unitPrice(new BigDecimal("50.00"))
                .discountApplied(10L)
                .totalPrice(new BigDecimal("225.00"))
                .status(ItemStatus.IN_CART)
                .user(user)
                .order(order)
                .timestamps(LocalDateTime.now())
                .build();

        // Assert
        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals(product, item.getProduct());
        assertEquals(5L, item.getQuantity());
        assertEquals(new BigDecimal("50.00"), item.getUnitPrice());
        assertEquals(10L, item.getDiscountApplied());
        assertEquals(new BigDecimal("225.00"), item.getTotalPrice());
        assertEquals(ItemStatus.IN_CART, item.getStatus());
        assertEquals(user, item.getUser());
        assertEquals(order, item.getOrder());
        assertNotNull(item.getTimestamps());
    }

    @Test
    void testItemSettersAndGetters() {
        // Arrange
        Item item = new Item();
        Product product = Product.builder().id(2L).build();
        User user = User.builder().id(2L).build();
        Order order = Order.builder().id(2L).build();
        LocalDateTime now = LocalDateTime.now();

        // Act
        item.setId(3L);
        item.setProduct(product);
        item.setQuantity(10L);
        item.setUnitPrice(new BigDecimal("100.00"));
        item.setDiscountApplied(5L);
        item.setTotalPrice(new BigDecimal("950.00"));
        item.setStatus(ItemStatus.IN_ORDER);
        item.setUser(user);
        item.setOrder(order);
        item.setTimestamps(now);

        // Assert
        assertEquals(3L, item.getId());
        assertEquals(product, item.getProduct());
        assertEquals(10L, item.getQuantity());
        assertEquals(new BigDecimal("100.00"), item.getUnitPrice());
        assertEquals(5L, item.getDiscountApplied());
        assertEquals(new BigDecimal("950.00"), item.getTotalPrice());
        assertEquals(ItemStatus.IN_ORDER, item.getStatus());
        assertEquals(user, item.getUser());
        assertEquals(order, item.getOrder());
        assertEquals(now, item.getTimestamps());
    }

    @Test
    void testItemNoArgsConstructor() {
        // Act
        Item item = new Item();

        // Assert
        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getProduct());
    }
}
