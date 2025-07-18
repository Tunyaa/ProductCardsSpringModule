package module.ProductCardsSpringModule.service;

import java.io.IOException;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.springframework.stereotype.Service;
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
    public void updateProduct(Product product, MultipartFile imageFile) throws IOException {

        // Получает продукт по id, из принимаемого продукта
        Product productById = productRepository.findById(product.getId()).orElseThrow();

        // Если новое изображение не передано, присваевается старый url
//        Не передаётся url из шаблона!!!
        if (!imageFile.isEmpty()) {
            // Если изображение меняется на новое, старое удаляется.
            imageService.deleteImage(productById.getImg());
            imageService.saveImageIfExist(imageFile, productById);
        }
        // Присваевает продукту измененные поля
        productById.setCurrentPrice(product.getCurrentPrice());
        productById.setCountry(product.getCountry());
        productById.setCategory(product.getCategory());
        productById.setDescription(product.getDescription());
        productById.setName(product.getName());
        productById.setPacking(product.getPacking());
        productById.setSeasonality(product.getSeasonality());
        productById.setVariety(product.getVariety());
        productById.setWhereBuy(product.getWhereBuy());

        // Обновляет пробукт в БД
        productRepository.save(productById);

    }

    // Удаляет продукт
    public void deleteProduct(Long id) {
        try {
            // Удаляет изображение затем объект из БД
            imageService.deleteImage(productRepository.findById(id).get().getImg());
        } catch (IOException ex) {
            System.getLogger(ProductService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        productRepository.deleteById(id);
    }
}
