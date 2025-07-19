package module.ProductCardsSpringModule.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.model.ProductCategories;
import module.ProductCardsSpringModule.repository.ProductRepository;
import module.ProductCardsSpringModule.service.ImageService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
@Controller
public class ProductController {

    private final ProductRepository productRepository; // УБРАТЬ ПОЛЕ
    private final ImageService imageService;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ImageService imageService, ProductService productService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.productService = productService;
    }

    // Показывает все продукты
    @GetMapping("/products")
    public String showProducts(Model model) {

//        List<Product> products = productRepository.findAll();
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "products";
    }

    // Отображение формы добавления нового продукта
    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) {

        // Передаёт продукт, который принимает шаблон, заполняет его поля 
        // и передаёт этот объект далее в post addProduct()
        model.addAttribute("product", new Product());

        // Передаёт в шаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);

        return "addproduct";
    }

    // Обработка формы добавления продукта
    @PostMapping("/addproduct")
    public String addProduct(
            // Принимает из шаблона объект продукт
            @ModelAttribute("product") Product product,
            // Принимает из шаблона объект картинку(необязательный параметр)
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        // Сохраняет изображение, если оно было загружено
        imageService.saveImageIfExist(imageFile, product);

        // Сохраняет продукт
        productService.saveProduct(product);

        // Добавляет сообщение об добавлении продукта
        model.addAttribute("message", "Продукт успешно добавлен!");

        // Возвращает ту же форму с сообщением
        model.addAttribute("product", new Product());

        return "addproduct";
    }

    // Отображение формы изменения продукта
    @GetMapping("/setproduct")
    public String showSetProductForm(
            // Принимает параметр id, который передаётся из шаблона "/products" из формы изменения
            @RequestParam Long id, Model model) throws IOException {

        // Получает продукт по id и передаёт в шаблон
//        Product product = productRepository.findById(id).orElseThrow();
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);

        // Передаёт в шаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);

        return "setproduct";
    }

    // Обработка формы добавления продукта
    @PostMapping("/setproduct")
    public String setProduct(
            // Принимает измененный продукт из шаблона
            @ModelAttribute("product") Product product,
            // Принимает из шаблона объект картинку(необязательный параметр)
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        // Сохраняет измененные поля продукта
        productService.updateProduct(product, imageFile);
        model.addAttribute("message", "Продукт изменен.");

        return "setproduct";
    }

    // Удаляет продукт по id
    @PostMapping("/delproduct")
    public String delProduct(@RequestParam Long id) {
        // Удаляет изображение затем объект из БД
        productService.deleteProduct(id);

        return "redirect:/products";
    }
}
