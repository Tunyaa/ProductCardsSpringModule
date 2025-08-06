package module.ProductCardsSpringModule.DTO;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author tunyaa
 */
public class PriceHistoryDTO {
    private List<BigDecimal> prices;

    private List<String> dates;

    public List<BigDecimal> getPrices() {
        return prices;
    }

    public void setPrices(List<BigDecimal> prices) {
        this.prices = prices;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
    
    
}
