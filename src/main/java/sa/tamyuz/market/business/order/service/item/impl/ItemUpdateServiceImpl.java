package sa.tamyuz.market.business.order.service.item.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sa.tamyuz.market.business.order.config.OrderDiscountConfig;
import sa.tamyuz.market.business.order.entity.Item;
import sa.tamyuz.market.business.order.enums.ItemStatus;
import sa.tamyuz.market.business.order.mapper.ItemMapper;
import sa.tamyuz.market.business.order.repository.ItemRepository;
import sa.tamyuz.market.business.order.schema.request.ReqItemUpdate;
import sa.tamyuz.market.business.order.schema.response.DtoItem;
import sa.tamyuz.market.business.order.service.item.ItemUpdateService;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.user.entity.User;
import sa.tamyuz.market.business.user.enums.Role;
import sa.tamyuz.market.business.user.repository.UserRepository;
import sa.tamyuz.market.general.exception.enums.ErrorCode;
import sa.tamyuz.market.general.exception.schema.response.BaseException;
import sa.tamyuz.market.general.security.util.JwtUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemUpdateServiceImpl implements ItemUpdateService {

    private final JwtUtil jwtUtil;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDiscountConfig orderDiscountConfig;

    @Override
    public List<DtoItem> update(ReqItemUpdate reqItemUpdate) {

        Product product =
                productRepository
                        .findById(reqItemUpdate.getProductId())
                        .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_PRODUCT));

        if (reqItemUpdate.getQuantity() > product.getQuantity()) {
            throw new BaseException(ErrorCode.NOT_ENOUGH_PRODUCT_QUANTITY);
        }

        User user = getCurrentUser();

        Item item = findItem(product, user);

        saveItem(item, product, user, reqItemUpdate);

        return itemRepository
                .findALlByUserIdAndStatus(user.getId(), ItemStatus.IN_CART)
                .stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    public User getCurrentUser() {
        String email = jwtUtil.findSubject();
        return userRepository
                .findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
    }

    private Item findItem(Product product, User user) {
        return itemRepository
                .findByProductIdAndUserIdAndStatus(product.getId(), user.getId(), ItemStatus.IN_CART)
                .orElseGet(() ->
                        Item
                                .builder()
                                .user(user)
                                .product(product)
                                .status(ItemStatus.IN_CART)
                                .build()
                );
    }

    private void saveItem(Item item, Product product, User user, ReqItemUpdate reqItemUpdate) {
        item.setQuantity(reqItemUpdate.getQuantity());
        item.setUnitPrice(product.getPrice());

        Long itemDiscountApplied = findItemDiscountApplied(user.getRole());
        item.setDiscountApplied(itemDiscountApplied);

        BigDecimal totalPrice =
                calculateItemTotalPrice(
                        itemDiscountApplied,
                        product.getPrice(),
                        reqItemUpdate.getQuantity()
                );
        item.setTotalPrice(totalPrice);

        itemRepository.save(item);

    }

    private Long findItemDiscountApplied(Role role) {

        if (Role.PREMIUM_USER.equals(role)) {
            return orderDiscountConfig.getPremiumUser();
        } else if (Role.USER.equals(role)) {
            return orderDiscountConfig.getUser();
        } else {
            return 0L;
        }

    }

    private BigDecimal calculateItemTotalPrice(Long discountPercentage, BigDecimal unitPrice, Long quantity) {

        // Handle zero quantity (return 0)
        if (quantity == 0L || unitPrice == null) {
            return BigDecimal.ZERO;
        }

        // Handle null or zero discount
        if (discountPercentage == null || discountPercentage == 0L) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }

        // Calculate discount amount
        BigDecimal discountAmount =
                unitPrice
                        .multiply(BigDecimal.valueOf(discountPercentage))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Calculate price after discount
        BigDecimal priceAfterDiscount = unitPrice.subtract(discountAmount);

        // Calculate total price
        BigDecimal totalPrice = priceAfterDiscount.multiply(BigDecimal.valueOf(quantity));

        // Set scale to 2 decimal places for currency
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

}
