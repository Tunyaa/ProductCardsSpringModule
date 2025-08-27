package module.ProductCardsSpringModule.controller;

import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.model.ProductCategories;
import module.ProductCardsSpringModule.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tunyaa
 */
@Controller
@RequestMapping("/execute")
public class ExecuteOrderController {

    private final OrderService orderService;

    public ExecuteOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/execute_order")
    public String executeOrderByOrderId(
            Model model,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String purchased
    ) {
        System.out.println("ID - " + id);
        // Если строка передана, выполнится поиск по категории
        if (category != null) {
            System.out.println("1");
            List<PositionDTO> positions = orderService.findByOrderIdAndProductCategory(category);
            model.addAttribute("positions", positions);
        } // Если строка передана, выполнится поиск по имени
        else if (productName != null) {
//            List<Product> products = productService.findProductByName(productName);
//            model.addAttribute("products", products);
        } else {
            // Передаёт весь список продуктов в шаблон
//            List<Product> allProducts = productService.getAllProducts();
//            model.addAttribute("products", allProducts);
        }

        if (id != null) {
            orderService.getPositionsByOrderId(id);
            List<PositionDTO> positions = orderService.getAllPositions();
            model.addAttribute("positions", positions);
        }

        // Передаёт в шаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);

        model.addAttribute("orderId", orderService.getCurrentOrderId());

        // Передаёт адрес запроса для формы в шаблон
        model.addAttribute("searchAction", "/execute/execute_order");

        return "execute_order";
    }

    @PostMapping("/update")
    public String updateExecutePosition(
            Model model,
            @ModelAttribute PositionDTO position,
            @RequestParam(required = false) Long orderId
    ) {

        orderService.updateExecutePosition(position);

        orderService.getPositionsByOrderId(orderId);
        return "redirect:/execute/execute_order?id=" + orderId;
    }

    @PostMapping("/switch_checkbox")
    public String switchCheckbox(@ModelAttribute PositionDTO position) {
        System.out.println(position.getPurchased() + "----------------------------------------");

        return "redirect:/execute/execute_order";
    }

}
