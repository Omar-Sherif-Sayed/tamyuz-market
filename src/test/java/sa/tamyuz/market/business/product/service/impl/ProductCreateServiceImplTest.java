package sa.tamyuz.market.business.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.repository.ProductRepository;
import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCreateServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductCreateServiceImpl productCreateService;

    private ReqProductCreate request;
    private Product product;

    @BeforeEach
    void setUp() {
        request = new ReqProductCreate();
        request.setName("Test Product");
        request.setDescription("Test Description");
        request.setPrice(new BigDecimal("99.99"));
        request.setQuantity(10L);

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10L)
                .delete(false)
                .build();
    }

    @Test
    void create_Success() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        DtoProduct result = productCreateService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(new BigDecimal("99.99"), result.getPrice());
        assertEquals(10L, result.getQuantity());
        assertFalse(result.getDelete());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void create_VerifyMappingAndSave() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        productCreateService.create(request);

        // Assert
        verify(productRepository).save(argThat(p ->
            p.getName().equals("Test Product") &&
            p.getDescription().equals("Test Description") &&
            p.getPrice().equals(new BigDecimal("99.99")) &&
            p.getQuantity().equals(10L) &&
            !p.getDelete()
        ));
    }

    @Test
    void create_WithLargeQuantity_Success() {
        // Arrange
        request.setQuantity(1000L);
        product.setQuantity(1000L);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        DtoProduct result = productCreateService.create(request);

        // Assert
        assertEquals(1000L, result.getQuantity());
    }

    @Test
    void create_WithHighPrice_Success() {
        // Arrange
        BigDecimal highPrice = new BigDecimal("9999.99");
        request.setPrice(highPrice);
        product.setPrice(highPrice);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        DtoProduct result = productCreateService.create(request);

        // Assert
        assertEquals(highPrice, result.getPrice());
    }
}
