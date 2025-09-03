package module.ProductCardsSpringModule.controller;

import java.util.ArrayList;
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

    // Возвращает список позиций из выбранного заказа.
    @GetMapping("/execute_order")
    public String executeOrderByOrderId(
            Model model,
            @RequestParam(required = false) Long id, // ID заказа
            @RequestParam(required = false) String category,// Фильтры позиций: по категории товара
            @RequestParam(required = false) String productName,//  по строке
            @RequestParam(required = false) String purchaseStatus//  по куплен\некуплен
    ) {

        List<PositionDTO> positions;

        // Если строка передана, выполнится поиск позиций по категории
        if (category != null) {
            positions = orderService.findPositionsByProductCategory(category);
        } // Если строка передана, выполнится поиск позиций по имени
        else if (productName != null) {
            positions = orderService.findPositionsByProductName(productName);
        } // Если строка передана, выполнится поиск позиций по статусу покупки
        else if (purchaseStatus != null) {
            positions = orderService.findPositionsByPurchaseStatus(purchaseStatus);
        } // Если id заказа передан, будет возвращен список позиций этого заказа
        else if (id != null) {
            orderService.getPositionsByOrderId(id);
            positions = orderService.getAllPositions();
        } // Иначе будет выполнен способ поиска, который выполнялся последним
        else {
            positions = orderService.getPositionsByLastRequest();
        }
        // Передаёт в модель список позиций
        model.addAttribute("positions", positions);
        // Передаёт в модель -> в форму -> в поле выбора категории список категорий
        List<ProductCategories> categories = Arrays.asList(ProductCategories.values());
        model.addAttribute("categories", categories);
        // Передаёт в модель id текущего заказа
        model.addAttribute("orderId", orderService.getCurrentOrderId());
        // Передаёт адрес  для формы в шаблон(фрагмент возвращение на предыдущую страницу)
        model.addAttribute("returnUrl", "/execute/execute_order");
        // Передаёт адрес  для формы в шаблон(фрагмент поиска)
        model.addAttribute("searchAction", "/execute/execute_order");
        // Сумма заказа
        model.addAttribute("purchaseAmount", orderService.purchaseAmount());

        return "execute_order";
    }

    // Обновляет позицию(статус, цена, количество, сумма)
    @PostMapping("/update")
    public String updateExecutePosition(
            Model model,
            @ModelAttribute PositionDTO position,
            @RequestParam(required = false) Long orderId
    ) {
        // Обновляет позицию
        orderService.updateExecutePosition(position);
        // Загружает список с обновленными полями
        orderService.getPositionsByOrderId(orderId);
        return "redirect:/execute/execute_order";
        // Возвращение без id, чтобы выполнилось условие else в /execute_order
        //        return "redirect:/execute/execute_order?id=" + orderId;
    }

    // Отменяет обновленную позицию
    // Статус, цена, количество, сумма возвращаются в исходное состояние
    @PostMapping("/switch_checkbox")
    public String switchCheckbox(@ModelAttribute PositionDTO position) {

        orderService.clearExicutePositionByID(position.getId());
        return "redirect:/execute/execute_order?id=" + orderService.getCurrentOrderId();
    }

    // Возарвщает список позиций последнего выбранного заказа
    @GetMapping("/current_execute_order")
    public String currentExecuteOrder() {

        if (orderService.getCurrentOrderId() == null) {
            return "redirect:/order/showorders";
        }
        return "redirect:/execute/execute_order?id=" + orderService.getCurrentOrderId();
    }

}
