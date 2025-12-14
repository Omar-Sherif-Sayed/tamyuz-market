package sa.tamyuz.market.business.product.mapper;

import org.junit.jupiter.api.Test;
import sa.tamyuz.market.business.product.entity.Product;
import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.request.ReqProductUpdate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void toDto_Success() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10L)
                .delete(false)
                .timestamps(timestamp)
                .build();

        // Act
        DtoProduct result = ProductMapper.toDto(product);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(new BigDecimal("99.99"), result.getPrice());
        assertEquals(10L, result.getQuantity());
        assertFalse(result.getDelete());
        assertEquals(timestamp, result.getTimestamps());
    }

    @Test
    void toEntity_FromReqProductCreate_Success() {
        // Arrange
        ReqProductCreate request = new ReqProductCreate();
        request.setName("New Product");
        request.setDescription("New Description");
        request.setPrice(new BigDecimal("149.99"));
        request.setQuantity(20L);

        // Act
        Product result = ProductMapper.toEntity(request);

        // Assert
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals("New Description", result.getDescription());
        assertEquals(new BigDecimal("149.99"), result.getPrice());
        assertEquals(20L, result.getQuantity());
        assertFalse(result.getDelete());
    }

    @Test
    void toEntity_FromReqProductCreate_DeleteIsFalseByDefault() {
        // Arrange
        ReqProductCreate request = new ReqProductCreate();
        request.setName("Product");
        request.setDescription("Description");
        request.setPrice(new BigDecimal("50.00"));
        request.setQuantity(5L);

        // Act
        Product result = ProductMapper.toEntity(request);

        // Assert
        assertFalse(result.getDelete());
    }

    @Test
    void toEntity_FromReqProductUpdate_Success() {
        // Arrange
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .price(new BigDecimal("50.00"))
                .quantity(5L)
                .delete(false)
                .build();

        ReqProductUpdate request = new ReqProductUpdate();
        request.setName("Updated Name");
        request.setDescription("Updated Description");
        request.setPrice(new BigDecimal("75.00"));
        request.setQuantity(15L);
        request.setDelete(true);

        // Act
        Product result = ProductMapper.toEntity(existingProduct, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId()); // ID should remain unchanged
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(new BigDecimal("75.00"), result.getPrice());
        assertEquals(15L, result.getQuantity());
        assertTrue(result.getDelete());
    }

    @Test
    void toEntity_FromReqProductUpdate_ReturnsSameInstance() {
        // Arrange
        Product product = Product.builder()
                .id(1L)
                .name("Product")
                .build();

        ReqProductUpdate request = new ReqProductUpdate();
        request.setName("Updated");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10.00"));
        request.setQuantity(1L);
        request.setDelete(false);

        // Act
        Product result = ProductMapper.toEntity(product, request);

        // Assert
        assertSame(product, result);
    }

    @Test
    void toEntity_FromReqProductUpdate_PreservesId() {
        // Arrange
        Product product = Product.builder()
                .id(999L)
                .name("Original")
                .build();

        ReqProductUpdate request = new ReqProductUpdate();
        request.setName("Updated");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10.00"));
        request.setQuantity(1L);
        request.setDelete(false);

        // Act
        Product result = ProductMapper.toEntity(product, request);

        // Assert
        assertEquals(999L, result.getId());
    }
}
