package module.ProductCardsSpringModule.DTO;

import java.math.BigDecimal;

/**
 *
 * @author tunyaa
 */
public class PurchaseAmountByConsumersDTO {

    private String consumer;
    private BigDecimal amount;

    public PurchaseAmountByConsumersDTO() {
    }

    public PurchaseAmountByConsumersDTO(String consumer, BigDecimal amount) {
        this.consumer = consumer;
        this.amount = amount;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
