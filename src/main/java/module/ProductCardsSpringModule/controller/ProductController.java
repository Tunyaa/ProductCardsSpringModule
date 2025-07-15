package module.ProductCardsSpringModule.controller;

import java.util.List;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tunyaa
 */
@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String showProductsMob(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "products";
    }
}
