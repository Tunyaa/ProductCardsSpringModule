package module.ProductCardsSpringModule.controller;

import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("position", new PositionDTO());

        // Передаёт весь список продуктов в шаблон
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);

        return "create-order";
    }

    // Создаёт новый заказ
    @PostMapping("/create")
    public String createOrder(RedirectAttributes redirectAttributes) {
        orderService.createOrder();
        redirectAttributes.addFlashAttribute("message", "Заказ создан!");

        return "redirect:/order/create";
    }

    // Добавляет повую позицию в positions, который передаётся в showCreateOrderForm()
    @PostMapping("/add")
    public String addPosition(@ModelAttribute PositionDTO position) {
        // Количество продукта должно быть больше 0. В шаблоне поле помечено required
        if (position.getQuantity() > 0) {
            orderService.addPosition(position);
        }
        return "redirect:/order/create";
    }

    // Очищает List positions
    @PostMapping("/clear")
    public String clearPositions() {
        orderService.clearPositions();
        return "redirect:/order/create";
    }

    // Удаляет выбранную позицию из List positions
    @PostMapping("/deletePosition")
    public String deletePosition(@ModelAttribute PositionDTO position) {
        orderService.deletePosition(position);

        return "redirect:/order/create";
    }

    // Показывает все подтвержденные заказы
    @GetMapping("/showorders")
    public String showOrder(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        return "orders";
    }

    // Показывает позиции выбранного заказа
    @GetMapping("/setorder")
    public String setOrder(Model model, @RequestParam Long id) {
        List<PositionDTO> positions = orderService.getPositionsByOrderId(id);
        return "redirect:/order/create";
    }
    
    // Удаляет заказ из БД
    @PostMapping("/deleteorder")
    public String deleteOrder(@ModelAttribute Order order){
        
        orderService.deleteOrderByID(order.getId());
        return "redirect:/order/showorders";
    }

}
