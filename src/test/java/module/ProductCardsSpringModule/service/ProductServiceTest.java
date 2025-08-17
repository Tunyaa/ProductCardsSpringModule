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
import org.mockito.stubbing.OngoingStubbing;

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
        // Подготовка мока
        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(1L);
        // Подготовка мока внутренний логики тестируемого метода
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        // Выполнение тестируемого метода
        productService.updateProduct(mockProduct, null);
        // Проверка количества вызова метода
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test // Удаляет продукты
    void deleteProduct_WhenProductExists_ShouldDeleteProductAndImage() throws IOException {
        // Аргументы теста
        String url = "/images/img.jpg";
        Long productID = 1L;
        // Настройка продукта
        Product product = new Product();
        product.setImg(url);

        // Подготовка мока внутренний логики тестируемого метода
        when(productRepository.findById(productID))
                .thenReturn(Optional.of(product));
        // Выполнение тестируемого метода
        productService.deleteProduct(productID);

        // Проверка вызова методов
        verify(productRepository).findById(productID);
        verify(productRepository).deleteById(productID);
        verify(imageService).deleteImage(url);
    }

    @Test
    void deleteProduct_WhenProductInOrder_ShouldLogException() {

        Long productId = 1L;
        String imageUrl = "/images/img.jpg";
        Product product = new Product();
        product.setImg(imageUrl);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doThrow(new RuntimeException("Foreign key violation"))
                .when(productRepository)
                .deleteById(productId);

        productService.deleteProduct(productId);

        // Проверяем, что методы были вызваны
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }
}
