package sa.tamyuz.market.business.order.service.order.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.order.config.OrderDiscountConfig;
import sa.tamyuz.market.business.order.entity.Item;
import sa.tamyuz.market.business.order.entity.Order;
import sa.tamyuz.market.business.order.enums.ItemStatus;
import sa.tamyuz.market.business.order.enums.OrderStatus;
import sa.tamyuz.market.business.order.mapper.OrderMapper;
import sa.tamyuz.market.business.order.repository.ItemRepository;
import sa.tamyuz.market.business.order.repository.OrderRepository;
import sa.tamyuz.market.business.order.schema.response.DtoOrder;
import sa.tamyuz.market.business.order.service.order.OrderCreateService;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.util.JwtUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCreateServiceImpl implements OrderCreateService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDiscountConfig orderDiscountConfig;

    /**
     * This function to create an order in the future to continue the flow <br>
     * the user has to pay in a specific time day or more if not, we have to delete the order <br>
     * and return quantity to the product.
     *
     * @return DtoOrder
     */
    @Transactional
    public DtoOrder create() {
        User user = getCurrentUser();

        List<Item> items = itemRepository.findALlByUserIdAndStatus(user.getId(), ItemStatus.IN_CART);

        validateItems(items);

        Order order = saveOrder(user, items);

        List<Product> products = new ArrayList<>();
        items.forEach(item -> {
            item.setStatus(ItemStatus.IN_ORDER);
            item.setOrder(order);

            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            products.add(product);
        });

        itemRepository.saveAll(items);
        productRepository.saveAll(products);

        log.info("Order created successfully with ID: {}", order.getId());

        return OrderMapper.toDto(order);
    }

    private void validateItems(List<Item> items) {
        if (items.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_ITEMS_IN_CARD);
        }

        items.forEach(item -> {
            if (item.getQuantity() > item.getProduct().getQuantity()) {
                throw new BaseException(ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY, item.getProduct().getName());
            }
        });
    }

    private Order saveOrder(User user, List<Item> items) {
        BigDecimal itemsTotalPrice = getCalculatedTotal(items);
        Long calculateTotalDiscountApplied = calculateTotalDiscountApplied(items, itemsTotalPrice);
        BigDecimal totalOrderPrice = calculateTotalOrderPrice(items, calculateTotalDiscountApplied, itemsTotalPrice);

        Order order =
                Order
                        .builder()
                        .user(user)
                        .status(OrderStatus.PENDING_PAYMENT)
                        .totalDiscountApplied(calculateTotalDiscountApplied)
                        .totalPrice(totalOrderPrice)
                        .build();
        return orderRepository.save(order);
    }


    public User getCurrentUser() {
        String email = jwtUtil.findSubject();
        return userRepository
                .findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
    }

    private Long calculateTotalDiscountApplied(List<Item> items, BigDecimal itemsTotalPrice) {
        Long discountFromItems = items.getFirst().getDiscountApplied();
        if (itemsTotalPrice.compareTo(new BigDecimal("500")) > 0) {
            discountFromItems += orderDiscountConfig.getAbove500();
        }
        return discountFromItems;
    }

    public BigDecimal getCalculatedTotal(List<Item> items) {
        return items
                .stream()
                .map(Item::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalOrderPrice(List<Item> items,
                                                Long calculateTotalDiscountApplied,
                                                BigDecimal itemsTotalPrice) {
        Long discountFromItems = items.getFirst().getDiscountApplied();

        if (calculateTotalDiscountApplied.equals(discountFromItems)) {
            return itemsTotalPrice;
        }

        long discountPercentage = calculateTotalDiscountApplied - discountFromItems;

        return
                itemsTotalPrice
                        .multiply(BigDecimal.valueOf(100 - discountPercentage))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

}
