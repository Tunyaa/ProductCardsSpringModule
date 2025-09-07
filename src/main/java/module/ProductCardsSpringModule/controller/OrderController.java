package module.ProductCardsSpringModule.controller;

import java.util.Arrays;
import java.util.List;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Сonsumer;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.model.ProductCategories;
import module.ProductCardsSpringModule.service.OrderService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author tunyaa
 */
@Controller
@RequestMapping("/order")
@Scope("session")
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
            Model model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Long orderId
    ) {
        // Передаёт список позиций в шаблон
        model.addAttribute("positions", orderService.getPositions());
        // Передаёт список потребителей в шаблон
        model.addAttribute("consumers", Arrays.asList(Сonsumer.values()));
        // Передаёт пустую позицию в шаблон
        model.addAttribute("position", new PositionDTO());
        // Передаёт адрес запроса для формы в шаблон
        model.addAttribute("searchAction", "/order/create");
        // Передаёт адрес страницы  с которой бутет выполнен переход по ссылке
        model.addAttribute("returnUrl", "/order/create");
        // Передаёт в шаблон -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);
        // id заказа для формы удаления заказа
        model.addAttribute("orderId", orderId);
        

        // Если строка передана, выполнится поиск по категории
        if (category != null) {
            List<Product> products = productService.findProductsByCategory(category);
            model.addAttribute("products", products);
        } // Если строка передана, выполнится поиск по имени
        else if (productName != null) {
            List<Product> products = productService.findProductByName(productName);
            model.addAttribute("products", products);
        } else {
            // Передаёт весь список продуктов в шаблон
            List<Product> allProducts = productService.getAllProducts();
            model.addAttribute("products", allProducts);
        }
        return "create-order";
    }

    // Создаёт новый заказ
    @PostMapping("/create")
    public String createOrder(RedirectAttributes redirectAttributes) {
        orderService.createOrder();
        orderService.clearPositions();
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
    public String setOrder(
            Model model,
            @RequestParam Long id
    ) {
        System.out.println("ID - " + id);
        orderService.getPositionsByOrderId(id);
        return "redirect:/order/create?orderId=" + id;
    }

    // Удаляет заказ из БД
    @PostMapping("/deleteorder")
    public String deleteOrder(@RequestParam Long orderId) {
        System.out.println(" ID del - " + orderId);
        orderService.deleteOrderByID(orderId);
        return "redirect:/order/showorders";
    }

}
