package module.ProductCardsSpringModule.controller;

import java.util.List;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
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
            @RequestParam(required = false) Long id
    ) {
        if (id != null) {
            orderService.getPositionsByOrderId(id);
        }

        List<PositionDTO> positions = orderService.getAllPositions();

        model.addAttribute("positions", positions);
        model.addAttribute("orderId", id);
        return "execute_order";
    }

    @PostMapping("/update")
    public String updateExecutePosition(
            Model model,
            @ModelAttribute PositionDTO position,
            @RequestParam Long orderId
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
