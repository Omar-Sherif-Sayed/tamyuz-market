package sa.tamyuz.market.business.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import sa.tamyuz.market.business.product.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10L)
                .delete(false)
                .build();
    }

    @Test
    void save_NewProduct_Success() {
        // Act
        Product savedProduct = productRepository.save(testProduct);

        // Assert
        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
        assertEquals("Test Description", savedProduct.getDescription());
        assertEquals(new BigDecimal("99.99"), savedProduct.getPrice());
        assertEquals(10L, savedProduct.getQuantity());
        assertFalse(savedProduct.getDelete());
    }

    @Test
    void findById_ExistingProduct_ReturnsProduct() {
        // Arrange
        Product savedProduct = entityManager.persist(testProduct);
        entityManager.flush();

        // Act
        Optional<Product> result = productRepository.findById(savedProduct.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(savedProduct.getId(), result.get().getId());
        assertEquals("Test Product", result.get().getName());
    }

    @Test
    void findById_NonExistingProduct_ReturnsEmpty() {
        // Act
        Optional<Product> result = productRepository.findById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ReturnsAllProducts() {
        // Arrange
        Product product1 = Product.builder()
                .name("Product 1")
                .description("Description 1")
                .price(new BigDecimal("50.00"))
                .quantity(5L)
                .delete(false)
                .build();

        Product product2 = Product.builder()
                .name("Product 2")
                .description("Description 2")
                .price(new BigDecimal("75.00"))
                .quantity(8L)
                .delete(false)
                .build();

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        // Act
        List<Product> products = productRepository.findAll();

        // Assert
        assertTrue(products.size() >= 2);
    }

    @Test
    void findAll_WithPagination_ReturnsPagedResults() {
        // Arrange
        for (int i = 0; i < 5; i++) {
            Product product = Product.builder()
                    .name("Product " + i)
                    .description("Description " + i)
                    .price(new BigDecimal("10.00"))
                    .quantity(1L)
                    .delete(false)
                    .build();
            entityManager.persist(product);
        }
        entityManager.flush();

        // Act
        Page<Product> page = productRepository.findAll(PageRequest.of(0, 3));

        // Assert
        assertTrue(page.getContent().size() <= 3);
    }

    @Test
    void save_UpdateExistingProduct_Success() {
        // Arrange
        Product savedProduct = entityManager.persist(testProduct);
        entityManager.flush();

        // Act
        savedProduct.setName("Updated Product");
        savedProduct.setPrice(new BigDecimal("149.99"));
        Product updatedProduct = productRepository.save(savedProduct);

        // Assert
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(new BigDecimal("149.99"), updatedProduct.getPrice());
    }

    @Test
    void delete_ExistingProduct_Success() {
        // Arrange
        Product savedProduct = entityManager.persist(testProduct);
        entityManager.flush();
        Long productId = savedProduct.getId();

        // Act
        productRepository.delete(savedProduct);
        entityManager.flush();

        // Assert
        Optional<Product> result = productRepository.findById(productId);
        assertFalse(result.isPresent());
    }

    @Test
    void save_ProductWithZeroQuantity_Success() {
        // Arrange
        testProduct.setQuantity(0L);

        // Act
        Product savedProduct = productRepository.save(testProduct);

        // Assert
        assertNotNull(savedProduct.getId());
        assertEquals(0L, savedProduct.getQuantity());
    }

    @Test
    void save_ProductWithLargePrice_Success() {
        // Arrange
        testProduct.setPrice(new BigDecimal("9999999.99"));

        // Act
        Product savedProduct = productRepository.save(testProduct);

        // Assert
        assertNotNull(savedProduct.getId());
        assertEquals(new BigDecimal("9999999.99"), savedProduct.getPrice());
    }

    @Test
    void save_DeletedProduct_Success() {
        // Arrange
        testProduct.setDelete(true);

        // Act
        Product savedProduct = productRepository.save(testProduct);

        // Assert
        assertNotNull(savedProduct.getId());
        assertTrue(savedProduct.getDelete());
    }

    @Test
    void count_ReturnsCorrectCount() {
        // Arrange
        entityManager.persist(testProduct);
        entityManager.persist(Product.builder()
                .name("Product 2")
                .description("Desc 2")
                .price(new BigDecimal("50.00"))
                .quantity(5L)
                .delete(false)
                .build());
        entityManager.flush();

        // Act
        long count = productRepository.count();

        // Assert
        assertTrue(count >= 2);
    }
}
