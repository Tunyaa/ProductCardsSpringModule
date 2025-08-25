package module.ProductCardsSpringModule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tunyaa
 */
@Controller
@RequestMapping("/execute")
public class ExecuteOrderController {

    @GetMapping("/execute_order")
    public String executeOrderByOrderId(
            Model model
    ) {
        return "execute_order";
    }
}
