package module.ProductCardsSpringModule.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import module.ProductCardsSpringModule.DTO.PriceHistoryDTO;
import module.ProductCardsSpringModule.model.PriceHistory;
import module.ProductCardsSpringModule.model.Product;
import module.ProductCardsSpringModule.service.PriceHistoryService;
import module.ProductCardsSpringModule.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @GetMapping("/pricehistory")
    public String priceHistory(
            Model model,
            @ModelAttribute("product") Product product) {
        PriceHistoryDTO priceHistory = priceHistoryService.getPriceHistoryDTOById(product.getId());

        model.addAttribute("product", product);

        model.addAttribute("prices", priceHistory.getPrices());
        model.addAttribute("dates", priceHistory.getDates());
        return "pricehistory";
    }
}
