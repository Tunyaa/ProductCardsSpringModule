package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test   // Проверяет вызов метода сохранения
    void saveProduct_ShouldCallRepositorySave() {
        // Подготовка объекта
        Product testProduct = new Product();

        // Выполнение тестируемого метода
        productService.saveProduct(testProduct);

        // Проверка количества вызова метода
        verify(productRepository, times(1)).save(testProduct); // Проверяем, что save вызван 1 раз
        // Дополнительная проверка: никакие другие методы репозитория не вызывались
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void updateProduct_ShouldResetCurrentProduct() throws IOException {
        // Подготовка мокОбъекта
        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(1L);
        // Подготовка мока внутренний логики тестируемого метода
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        // Выполнение тестируемого метода
        productService.updateProduct(mockProduct, null);
        // Проверка количества вызова метода
        verify(productRepository, times(1)).save(mockProduct);
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
