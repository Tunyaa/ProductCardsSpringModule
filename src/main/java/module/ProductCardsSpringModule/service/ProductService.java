package module.ProductCardsSpringModule.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import module.ProductCardsSpringModule.model.PriceHistory;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.PriceHistoryRepository;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductService(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    // Сохраняет продукт
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Изменяет продукт
    @Transactional
    public void updateProduct(Product product, MultipartFile imageFile) throws IOException {

        // Получает продукт по id, из принимаемого продукта
        Product productById = productRepository.findById(product.getId()).orElseThrow();

        // Если новое изображение не передано, присваивается старый url
//        Не передаётся url из шаблона!!!
        if (imageFile != null && !imageFile.isEmpty()) {
            // Если изображение меняется на новое, старое удаляется.            
            imageService.deleteImage(productById.getImg());
            imageService.saveImageIfExist(imageFile, productById);
        }
        // Присваивает продукту измененные поля
        productById.setCurrentPrice(product.getCurrentPrice());
        productById.setCountry(product.getCountry());
        productById.setCategory(product.getCategory());
        productById.setDescription(product.getDescription());
        productById.setName(product.getName());
        productById.setPacking(product.getPacking());
        productById.setSeasonality(product.getSeasonality());
        productById.setVariety(product.getVariety());
        productById.setWhereBuy(product.getWhereBuy());

        // Обновляет продукт в БД
        productRepository.save(productById);

    }

    // Удаляет продукт
    public void deleteProduct(Long id) {
        try {
            // Получает url изображения
            String img = productRepository.findById(id).get().getImg();

            // Удаляет объект из БД затем изображение  
                productRepository.deleteById(id);
            imageService.deleteImage(img);
        } catch (IOException ex) {
            System.getLogger(ProductService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (Exception ex) {
            System.getLogger(ProductService.class.getName()).log(System.Logger.Level.ERROR, "ЭТОТ ПРОДУКТ НЕ МОЖЕТ БЫТЬ УДАЛЕН, ТАК КАК ПОЗИЦИЯ С ЭТИМ ПРОДУКТОМ УЖЕ ЕСТЬ В ЗАКАЗЕ.");
        }

    }
    
    

    // Возвращает List всех продуктов
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    // Возвращает продукт по id
    public Product findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return product;
    }
}
