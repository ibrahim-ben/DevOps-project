import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;
import tn.esprit.devops_project.services.ProductServiceImpl;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceImplTest {
    @MockBean
    ProductRepository productRepository;

    @MockBean
    StockRepository stockRepository;

    @Autowired
    ProductServiceImpl productService; // This is the real service, not a mock

    @Autowired
    MeterRegistry meterRegistry; // Inject the MeterRegistry from Spring


    @Test
    public void addProductTest() {
        Stock stock = new Stock(2L, "StocknumberN", null); 
        Product product = new Product(2L, "ProductnumberN", 11.0f, 33, ProductCategory.ELECTRONICS, stock);
        when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);
        Product addedProduct = productService.addProduct(product, 2L);

        assertNotNull(addedProduct);
        assertEquals("ProductnumberN", addedProduct.getTitle());

        Counter customCounter = meterRegistry.find("custom_metric_name").counter();
        assertNotNull(customCounter);
        assertEquals(1.0, customCounter.count(), 0.0); 
    }

    @Test
    public void addProductStockNotFoundTest() {
        when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Stock stock = new Stock(2L, "StocknumberN", null);
        Product product = new Product(2L, "ProductnumberN", 11.0f, 33, ProductCategory.ELECTRONICS, stock);

        Assertions.assertThrows(NullPointerException.class, () -> productService.addProduct(product, 2L));
    }


    @Test
    public void retrieveProductTest() {
        Product product = new Product(2L, "Product1", 10.0f, 33, ProductCategory.ELECTRONICS, null);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.retrieveProduct(2L);

        assertNotNull(retrievedProduct);
        assertEquals("Product1", retrievedProduct.getTitle());
    }
    @Test
    public void retrieveProductNotFoundTest() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NullPointerException.class, () -> {
            productService.retrieveProduct(1L);
        });
    }

    @Test
    public void retrieveAllProductTest() {
        when(productRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Product(1L, "Product1", 10.0f, 33, ProductCategory.ELECTRONICS, null),
                        new Product(2L, "Product2", 20.0f, 25, ProductCategory.CLOTHING, null),
                        new Product(3L, "Product3", 15.0f, 89, ProductCategory.BOOKS, null)
                )
        );

        List<Product> productList = productService.retreiveAllProduct();

        assertEquals(3, productList.size());
    }

    @Test
    public void deleteProductTest() {
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }




}
