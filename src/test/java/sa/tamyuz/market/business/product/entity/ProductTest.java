package sa.tamyuz.market.business.product.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductBuilder() {
        // Arrange & Act
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10L)
                .delete(false)
                .timestamps(LocalDateTime.now())
                .build();

        // Assert
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals(10L, product.getQuantity());
        assertFalse(product.getDelete());
        assertNotNull(product.getTimestamps());
    }

    @Test
    void testProductSettersAndGetters() {
        // Arrange
        Product product = new Product();
        LocalDateTime now = LocalDateTime.now();

        // Act
        product.setId(2L);
        product.setName("Updated Product");
        product.setDescription("Updated Description");
        product.setPrice(new BigDecimal("149.99"));
        product.setQuantity(20L);
        product.setDelete(true);
        product.setTimestamps(now);

        // Assert
        assertEquals(2L, product.getId());
        assertEquals("Updated Product", product.getName());
        assertEquals("Updated Description", product.getDescription());
        assertEquals(new BigDecimal("149.99"), product.getPrice());
        assertEquals(20L, product.getQuantity());
        assertTrue(product.getDelete());
        assertEquals(now, product.getTimestamps());
    }

    @Test
    void testProductNoArgsConstructor() {
        // Act
        Product product = new Product();

        // Assert
        assertNotNull(product);
        assertNull(product.getId());
        assertNull(product.getName());
    }

    @Test
    void testProductDefaultDeleteValue() {
        // Act
        Product product = new Product();

        // Assert
        assertNull(product.getDelete());
    }
}
