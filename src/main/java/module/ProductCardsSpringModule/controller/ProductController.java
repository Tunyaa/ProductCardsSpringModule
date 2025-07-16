package module.ProductCardsSpringModule.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.model.ProductCategories;
import module.ProductCardsSpringModule.repository.ProductRepository;
import module.ProductCardsSpringModule.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tunyaa
 */
@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductController(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    // Показать все продукты
    @GetMapping("/products")
    public String showProducts(Model model) {

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "products";
    }

    // Отображение формы добавления нового продукта
    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) {

        // Передаём продукт, который принимает шаблон, заполняет его поля 
        // и передаёт этот объект далее в post addProduct()
        model.addAttribute("product", new Product());

        // Передаём в ышаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);

        return "addproduct";
    }

    // Обработка формы добавления продукта
    @PostMapping("/addproduct")
    public String addProduct(
            // Принимаем из шаблона объект продукт
            @ModelAttribute("product") Product product,
            // Принимаем из шаблона объект картинку(необязательный параметр)
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        // Сохраняем изображение, если оно было загружено
        if (!imageFile.isEmpty()) {
            // Изображение копируется по url
            String fileName = imageService.saveImage(imageFile);
            // url сохраняется в поле продукта
            product.setImg("images/" + fileName);
        }

        // Сохраняем продукт
        Product save = productRepository.save(product);

        // Добавляем сообщение об добавлении продукта
        model.addAttribute("message", "Продукт успешно добавлен!");

        // Возвращаем ту же форму с сообщением
        model.addAttribute("product", new Product());

        return "addproduct";
    }

    // Отображение формы изменения продукта
    @GetMapping("/setproduct")
    public String showSetProductForm(
            // Принимаем параметр id, который передаётся из шаблона "/products" из формы изменения
            @RequestParam Long id, Model model) throws IOException {

        // Получаем продукт по id и передаём в шаблон
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);

        // Передаём в шаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);

        return "setproduct";
    }

    // Обработка формы добавления продукта
    @PostMapping("/setproduct")
    public String setProduct(
            // Принимаем измененный продукт из шаблона
            @ModelAttribute("product") Product product,
            // Принимаем из шаблона объект картинку(необязательный параметр)
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        // Получаем продукт по id, из принимаемого продукта
        Product productById = productRepository.findById(product.getId()).orElseThrow();

        // Если новое изображение не передано, присваевается старый url
//        Не передаётся url из шаблона!!!
        if (!imageFile.isEmpty()) {
            String fileName = imageService.saveImage(imageFile);
            product.setImg("images/" + fileName);
        } else {
            product.setImg(productById.getImg());
        }

        // Обновляем пробукт в БД
        productRepository.save(product);
        model.addAttribute("message", "Продукт изменен.");

        return "setproduct";
    }

    // Удаление продукта по id
    @PostMapping("/delproduct")
    public String delProduct(@RequestParam Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
