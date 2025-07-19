package module.ProductCardsSpringModule.controller;

import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.model.Сonsumer;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.service.OrderService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String showCreateOrderForm(
            Model model
    ) {
        // Передаёт список позиций в шаблон
        model.addAttribute("positions", orderService.getAllPositions());

        // Передаёт список потребителей в шаблон
        model.addAttribute("consumers", Arrays.asList(Сonsumer.values()));

        // Передаёт пустую позицию в шаблон
        model.addAttribute("position", new Position());

        // Передаёт весь список продуктов в шаблон
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);

        return "/create-order";
    }

    @PostMapping("/add")
    public String addPosition(@ModelAttribute Position position) {

        if (position.getQuantity() != 0) {

            orderService.addPosition(position);
        }
        return "redirect:/order/create";
    }
//    @PostMapping("/add")
//    public String addPosition(
//            @ModelAttribute("position") Position position,            
//            Model model
//    ) {
//        if (position.getQuantity() != 0) {
//
//            orderService.addPosition(position);
//        }
//        
//        return "fragments/position-list :: position-list"; // Возвращаем только фрагмент таблицы
//    }

    @PostMapping("/clear")
    public String clearPositions() {
        orderService.clearPositions();
        return "redirect:/order/create";
    }

}
