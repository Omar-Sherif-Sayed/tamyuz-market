package sa.tamyuz.market.business.order.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import sa.tamyuz.market.business.order.entity.Order;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private User testUser;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .deleted(false)
                .build();
        entityManager.persist(testUser);

        testOrder = Order.builder()
                .user(testUser)
                .status(OrderStatus.PENDING_PAYMENT)
                .totalDiscountApplied(0L)
                .totalPrice(new BigDecimal("500.00"))
                .build();
    }

    @Test
    void save_NewOrder_Success() {
        // Act
        Order savedOrder = orderRepository.save(testOrder);

        // Assert
        assertNotNull(savedOrder.getId());
        assertEquals(testUser.getId(), savedOrder.getUser().getId());
        assertEquals(OrderStatus.PENDING_PAYMENT, savedOrder.getStatus());
        assertEquals(new BigDecimal("500.00"), savedOrder.getTotalPrice());
    }

    @Test
    void findById_ExistingOrder_ReturnsOrder() {
        // Arrange
        Order savedOrder = entityManager.persist(testOrder);
        entityManager.flush();

        // Act
        Optional<Order> result = orderRepository.findById(savedOrder.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedOrder.getId(), result.get().getId());
        assertEquals(OrderStatus.PENDING_PAYMENT, result.get().getStatus());
    }

    @Test
    void findById_NonExistingOrder_ReturnsEmpty() {
        // Act
        Optional<Order> result = orderRepository.findById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void save_UpdateOrderStatus_Success() {
        // Arrange
        Order savedOrder = entityManager.persist(testOrder);
        entityManager.flush();

        // Act
        savedOrder.setStatus(OrderStatus.COMPLETED);
        Order updatedOrder = orderRepository.save(savedOrder);

        // Assert
        assertEquals(OrderStatus.COMPLETED, updatedOrder.getStatus());
    }

    @Test
    void save_OrderWithDiscount_Success() {
        // Arrange
        testOrder.setTotalDiscountApplied(10L);
        testOrder.setTotalPrice(new BigDecimal("450.00"));

        // Act
        Order savedOrder = orderRepository.save(testOrder);

        // Assert
        assertNotNull(savedOrder.getId());
        assertEquals(10L, savedOrder.getTotalDiscountApplied());
        assertEquals(new BigDecimal("450.00"), savedOrder.getTotalPrice());
    }

    @Test
    void delete_ExistingOrder_Success() {
        // Arrange
        Order savedOrder = entityManager.persist(testOrder);
        entityManager.flush();
        Long orderId = savedOrder.getId();

        // Act
        orderRepository.delete(savedOrder);
        entityManager.flush();

        // Assert
        Optional<Order> result = orderRepository.findById(orderId);
        assertFalse(result.isPresent());
    }

    @Test
    void save_OrderWithDifferentStatuses_Success() {
        // Arrange & Act
        Order pendingOrder = Order.builder()
                .user(testUser)
                .status(OrderStatus.PENDING_PAYMENT)
                .totalDiscountApplied(0L)
                .totalPrice(new BigDecimal("100.00"))
                .build();
        Order savedPending = orderRepository.save(pendingOrder);

        Order completedOrder = Order.builder()
                .user(testUser)
                .status(OrderStatus.COMPLETED)
                .totalDiscountApplied(5L)
                .totalPrice(new BigDecimal("200.00"))
                .build();
        Order savedCompleted = orderRepository.save(completedOrder);

        // Assert
        assertEquals(OrderStatus.PENDING_PAYMENT, savedPending.getStatus());
        assertEquals(OrderStatus.COMPLETED, savedCompleted.getStatus());
    }

    @Test
    void count_ReturnsCorrectCount() {
        // Arrange
        entityManager.persist(testOrder);
        entityManager.persist(Order.builder()
                .user(testUser)
                .status(OrderStatus.COMPLETED)
                .totalDiscountApplied(0L)
                .totalPrice(new BigDecimal("300.00"))
                .build());
        entityManager.flush();

        // Act
        long count = orderRepository.count();

        // Assert
        assertTrue(count >= 2);
    }
}
