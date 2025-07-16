package module.ProductCardsSpringModule.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import module.ProductCardsSpringModule.model.Product;
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

    @GetMapping("/products")
    public String showProductsMob(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "products";
    }

    // Отображение формы
    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());

        List<String> categories = Arrays.asList("Овощи", "Фрукты", "Ягоды", "Орехи", "Сухофрукты", "Зелень", "Экзотика", "Грибы");

        model.addAttribute("categories", categories);

        return "addproduct";
    }

    // Обработка отправки формы
    @PostMapping("/addproduct")
    public String addProduct(
            @ModelAttribute("product") Product product,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        // Сохраняем изображение, если оно было загружено
        if (!imageFile.isEmpty()) {

            String fileName = imageService.saveImage(imageFile);
            product.setImg("images/" + fileName);
        }

        if (product.getName().equals("")) {
            model.addAttribute("message", "Название продукта не введено!");
            return "addproduct";
        }
        Product save = productRepository.save(product);
        // Добавляем сообщение об 

        model.addAttribute("message", "Продукт успешно добавлен!");

        // Возвращаем ту же форму с сообщением
        model.addAttribute("product", new Product());
        return "addproduct";
    }

    @GetMapping("/setproduct")
    public String showSetProductForm(@RequestParam Long id, Model model) throws IOException {

        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        List<String> categories = Arrays.asList("Овощи", "Фрукты", "Ягоды", "Орехи", "Сухофрукты", "Зелень", "Экзотика", "Грибы");

        model.addAttribute("categories", categories);
        return "setproduct";
    }

    @PostMapping("/setproduct")
    public String setProduct(
            @ModelAttribute("product") Product product,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {

        Product productById = productRepository.findById(product.getId()).orElseThrow();

        productById.setName(product.getName());
        productById.setCurrentPrice(product.getCurrentPrice());
        productById.setCategory(product.getCategory());
        productById.setCountry(product.getCountry());
        productById.setDescription(product.getDescription());
        productById.setPacking(product.getPacking());
        productById.setSeasonality(product.getSeasonality());
        productById.setVariety(product.getVariety());
        productById.setWhereBuy(product.getWhereBuy());

        if (!imageFile.isEmpty()) {

            String fileName = imageService.saveImage(imageFile);
            productById.setImg("images/" + fileName);
        }

        productRepository.save(productById);

        model.addAttribute("message", "Продукт изменен.");

        return "setproduct";
    }

    @PostMapping("/delproduct")
    public String delProduct(
            @RequestParam Long id){
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        productRepository.deleteById(id);
        
        return "redirect:/products";
        
    }
}
