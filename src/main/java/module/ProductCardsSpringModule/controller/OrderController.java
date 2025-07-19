package module.ProductCardsSpringModule.controller;

import java.util.List;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.service.OrderService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tunyaa
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;

    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    // Отображение формы создания заказа
    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        // Передаёт новый пустой заказ в шаблон
        model.addAttribute("order", new Order());
        
        // Передаёт пустую позицию в шаблон
        model.addAttribute("position", new Position());

        // Передаёт весь список продуктов в шаблон
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);

        return "/create-order";
    }

    @PostMapping("/create")
    public String createOrder() {
        return "/orders";
    }
}
