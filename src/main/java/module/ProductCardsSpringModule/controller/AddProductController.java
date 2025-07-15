package module.ProductCardsSpringModule.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.ProductRepository;
import module.ProductCardsSpringModule.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author tunyaa
 */
@Controller
public class AddProductController {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public AddProductController(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    // Отображение формы
    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());

        List<String> categories = Arrays.asList("Овощь", "Фрукт", "Ягоды", "Орехи");

        model.addAttribute("categories", categories);

        return "addproduct";
    }

    // Обработка отправки формы
    @PostMapping("/addproduct")
    public String addProduct(
            @ModelAttribute("product") Product product,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            Model model) throws IOException {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&                   method on())");

        System.out.println(imageFile.getName());
        // Сохраняем изображение, если оно было загружено
        if (!imageFile.isEmpty()) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&                   if (!imageFile.isEmpty())");
            String fileName = imageService.saveImage(imageFile);
            product.setImg("images/" + fileName);
        }

        Product save = productRepository.save(product);
        // Добавляем сообщение об 

        model.addAttribute("message", "Продукт успешно добавлен!");

        // Возвращаем ту же форму с сообщением
        model.addAttribute("product", new Product());
        return "addproduct";
    }

}
