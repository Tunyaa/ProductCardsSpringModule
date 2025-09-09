package module.ProductCardsSpringModule.controller;

import module.ProductCardsSpringModule.DTO.PriceHistoryDTO;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.service.PriceHistoryService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tunyaa
 */
@Controller
public class PriceHistoryController {

    private final ProductService productService;
    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(ProductService productService, PriceHistoryService priceHistoryService) {
        this.productService = productService;
        this.priceHistoryService = priceHistoryService;
    }

    // Возвращает список цен выбраного продукта
    @GetMapping("/pricehistory")
    public String priceHistory(
            Model model,
            @ModelAttribute("product") Product product,
            @RequestParam(name = "returnUrl", required = false) String returnUrl
    ) {
        PriceHistoryDTO priceHistory = priceHistoryService.getPriceHistoryDTOById(product.getId());
        product = productService.findProductById(product.getId());
        // Продукт
        model.addAttribute("product", product);
        // Передаёт адрес  для формы в шаблон(фрагмент возвращение на предыдущую страницу)
        model.addAttribute("returnUrl", returnUrl);
        // Список цен на продукт
        model.addAttribute("prices", priceHistory.getPrices());
        // Список дат
        model.addAttribute("dates", priceHistory.getDates());
        return "pricehistory";
    }
}
