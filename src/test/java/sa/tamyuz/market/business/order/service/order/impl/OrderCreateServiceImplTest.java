package sa.tamyuz.market.business.order.service.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sa.tamyuz.market.business.order.config.OrderDiscountConfig;
import sa.tamyuz.market.business.order.entity.Item;
import sa.tamyuz.market.business.order.entity.Order;
import sa.tamyuz.market.business.order.enums.ItemStatus;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.order.repository.ItemRepository;
import sa.tamyuz.market.business.order.repository.OrderRepository;
import sa.tamyuz.market.business.order.schema.response.DtoOrder;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.util.JwtUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCreateServiceImplTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDiscountConfig orderDiscountConfig;

    @InjectMocks
    private OrderCreateServiceImpl orderCreateService;

    private User user;
    private Product product;
    private Item item;
    private Order order;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(Role.USER)
                .deleted(false)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(new BigDecimal("100.00"))
                .quantity(20L)
                .delete(false)
                .build();

        item = Item.builder()
                .id(1L)
                .product(product)
                .quantity(5L)
                .unitPrice(new BigDecimal("100.00"))
                .totalPrice(new BigDecimal("500.00"))
                .discountApplied(0L)
                .status(ItemStatus.IN_CART)
                .user(user)
                .build();

        order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDING_PAYMENT)
                .totalDiscountApplied(0L)
                .totalPrice(new BigDecimal("500.00"))
                .build();
    }

    @Test
    void create_Success() {
        // Arrange
        List<Item> items = List.of(item);
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class))).thenReturn(items);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(itemRepository.saveAll(anyList())).thenReturn(items);
        when(productRepository.saveAll(anyList())).thenReturn(List.of(product));

        // Act
        DtoOrder result = orderCreateService.create();

        // Assert
        assertNotNull(result);
        assertEquals(OrderStatus.PENDING_PAYMENT, result.getStatus());
        verify(orderRepository).save(any(Order.class));
        verify(itemRepository).saveAll(anyList());
        verify(productRepository).saveAll(anyList());
    }

    @Test
    void create_EmptyCart_ThrowsException() {
        // Arrange
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class)))
                .thenReturn(new ArrayList<>());

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> {
            orderCreateService.create();
        });

        assertEquals(ErrorCode.NOT_FOUND_ITEMS_IN_CARD, exception.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void create_InsufficientProductQuantity_ThrowsException() {
        // Arrange
        item.setQuantity(30L); // More than product quantity (20)
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class)))
                .thenReturn(List.of(item));

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> {
            orderCreateService.create();
        });

        assertEquals(ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY, exception.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void create_UserNotFound_ThrowsException() {
        // Arrange
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        BaseException exception = assertThrows(BaseException.class, () -> {
            orderCreateService.create();
        });

        assertEquals(ErrorCode.NOT_FOUND_USER, exception.getErrorCode());
    }

    @Test
    void create_UpdatesProductQuantity() {
        // Arrange
        List<Item> items = List.of(item);
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class))).thenReturn(items);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(itemRepository.saveAll(anyList())).thenReturn(items);
        when(productRepository.saveAll(anyList())).thenReturn(List.of(product));

        Long initialQuantity = product.getQuantity();

        // Act
        orderCreateService.create();

        // Assert
        verify(productRepository).saveAll(argThat(products -> {

            List<Product> productList = new ArrayList<>();
            products.forEach(productList::add);

            if (productList.isEmpty()) {
                return false;
            }

            Product savedProduct = productList.getFirst();
            return savedProduct.getQuantity().equals(initialQuantity - item.getQuantity());
        }));
    }

    @Test
    void create_UpdatesItemStatus() {
        // Arrange
        List<Item> items = List.of(item);
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class))).thenReturn(items);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(itemRepository.saveAll(anyList())).thenReturn(items);
        when(productRepository.saveAll(anyList())).thenReturn(List.of(product));

        // Act
        orderCreateService.create();

        // Assert
        verify(itemRepository).saveAll(argThat(savedItems -> {

            List<Item> itemList = new ArrayList<>();
            savedItems.forEach(itemList::add);

            if (itemList.isEmpty()) {
                return false;
            }

            Item savedItem = itemList.getFirst();
            return savedItem.getStatus() == ItemStatus.IN_ORDER;
        }));
    }

    @Test
    void create_WithDiscountAbove500_AppliesExtraDiscount() {
        // Arrange
        item.setTotalPrice(new BigDecimal("600.00"));
        List<Item> items = List.of(item);

        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));
        when(itemRepository.findALlByUserIdAndStatus(anyLong(), any(ItemStatus.class))).thenReturn(items);
        when(orderDiscountConfig.getAbove500()).thenReturn(5L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(itemRepository.saveAll(anyList())).thenReturn(items);
        when(productRepository.saveAll(anyList())).thenReturn(List.of(product));

        // Act
        orderCreateService.create();

        // Assert
        verify(orderDiscountConfig).getAbove500();
    }

    @Test
    void getCalculatedTotal_WithMultipleItems_CalculatesCorrectly() {
        // Arrange
        Item item1 = Item.builder().totalPrice(new BigDecimal("100.50")).build();
        Item item2 = Item.builder().totalPrice(new BigDecimal("200.25")).build();
        Item item3 = Item.builder().totalPrice(new BigDecimal("50.75")).build();
        List<Item> items = List.of(item1, item2, item3);

        // Act
        BigDecimal total = orderCreateService.getCalculatedTotal(items);

        // Assert
        assertEquals(new BigDecimal("351.50"), total);
    }

    @Test
    void getCalculatedTotal_WithNullValues_IgnoresNulls() {
        // Arrange
        Item item1 = Item.builder().totalPrice(new BigDecimal("100.00")).build();
        Item item2 = Item.builder().totalPrice(null).build();
        Item item3 = Item.builder().totalPrice(new BigDecimal("50.00")).build();
        List<Item> items = List.of(item1, item2, item3);

        // Act
        BigDecimal total = orderCreateService.getCalculatedTotal(items);

        // Assert
        assertEquals(new BigDecimal("150.00"), total);
    }

    @Test
    void getCurrentUser_Success() {
        // Arrange
        when(jwtUtil.findSubject()).thenReturn("test@example.com");
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(user));

        // Act
        User result = orderCreateService.getCurrentUser();

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}
