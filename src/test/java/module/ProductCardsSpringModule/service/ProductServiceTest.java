package module.ProductCardsSpringModule.service;

import java.util.LinkedList;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author tunyaa
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private ProductService productService;

    // Сохранить продукт
    @Test
    void saveProduct_ShouldCallRepositorySave() {
        // Arrange (подготовка)
        Product testProduct = new Product();
        testProduct.setName("Test Product");

        // Act (действие)
        productService.saveProduct(testProduct);

        // Assert (проверка)
        verify(productRepository, times(1)).save(testProduct); // Проверяем, что save вызван 1 раз
        // Дополнительная проверка: никакие другие методы репозитория не вызывались
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void saveProduct_SaveNewProduct_ShouldSaveProductInDB() {
        Product mockProduct = mock(Product.class);

        when(mockProduct.getName()).thenReturn("Poring");
        System.out.println(mockProduct.getName());
        verify(mockProduct).getName();
    }
    // Изменить продукт
    // Удалить продукт
    // Вернуть лист продуктов
    // Вернуть продукт по id
}
