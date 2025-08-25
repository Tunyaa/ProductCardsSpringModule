package module.ProductCardsSpringModule.controller;

import java.util.List;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam Long id
    ) {
        System.out.println(id + " - ID");
//        orderService.getPositionsByOrderId(id);
        
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "execute_order";
    }
}
