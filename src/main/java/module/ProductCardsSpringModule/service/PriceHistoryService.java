package module.ProductCardsSpringModule.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import module.ProductCardsSpringModule.DTO.PriceHistoryDTO;
import module.ProductCardsSpringModule.model.PriceHistory;
import module.ProductCardsSpringModule.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author tunyaa
 */
@Service
public class PriceHistoryService {

    private final ProductService productService;
    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryService(ProductService productService, PriceHistoryRepository priceHistoryRepository) {
        this.productService = productService;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    // Преобразовывает список priseHistory в priseHistoryDTO
    public PriceHistoryDTO getPriceHistoryDTOById(Long id) {

        List<PriceHistory> priceHistory = getPriceHistoryByPriductId(id);
        PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO();
        priceHistoryDTO.setPrices(priceHistory.stream()
                .map(PriceHistory::getPrice).
                collect(Collectors.toList()));

        List<LocalDateTime> priceHistorydates = priceHistory.stream()
                .map(PriceHistory::getChangeDate)
                .collect(Collectors.toList());
        List<String> dates = new ArrayList<>();
        for (LocalDateTime date : priceHistorydates) {
            String format = date.format(DateTimeFormatter.ofPattern("dd.mm.yyyy"));
            dates.add(format);
        }

        priceHistoryDTO.setDates(dates);

        return priceHistoryDTO;
    }

//     Возвращает список истории цен на продукт по его id
    public List<PriceHistory> getPriceHistoryByPriductId(Long id) {
        List<PriceHistory> priceHistory = priceHistoryRepository.findAllByProductId(id);
        return priceHistory;
    }

}
