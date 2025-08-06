package module.ProductCardsSpringModule.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import module.ProductCardsSpringModule.DTO.PriceHistoryDTO;
import module.ProductCardsSpringModule.model.PriceHistory;
import org.springframework.stereotype.Service;

/**
 *
 * @author tunyaa
 */
@Service
public class PriceHistoryService {

    private final ProductService productService;

    public PriceHistoryService(ProductService productService) {
        this.productService = productService;
    }

    public PriceHistoryDTO getPriceHistoryDTOById(Long id) {

        List<PriceHistory> priceHistory = productService.getPriceHistoryByPriductId(id);
        PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO();
        priceHistoryDTO.setPrices(priceHistory.stream().map(PriceHistory::getPrice).collect(Collectors.toList()));

        List<LocalDateTime> priceHistorydates = priceHistory.stream().map(PriceHistory::getChangeDate).collect(Collectors.toList());
        List<String> dates = new ArrayList<>();
        for (LocalDateTime date : priceHistorydates) {
            String format = date.format(DateTimeFormatter.ofPattern("dd.mm.yyyy"));
            dates.add(format);
        }

        priceHistoryDTO.setDates(dates);

        return priceHistoryDTO;

    }

}
