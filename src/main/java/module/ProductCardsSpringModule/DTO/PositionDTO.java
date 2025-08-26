package module.ProductCardsSpringModule.DTO;

import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;
import module.ProductCardsSpringModule.model.Product;

/**
 *
 * @author tunyaa
 */
public class PositionDTO {

    public UUID id;

    private Long productId;

    private Product product;

    private double quantity;

    private String consumer;

    private BigDecimal buyingPrice;

    private double purchasedQuantity;

    private BigDecimal purchaseAmount;

    private Boolean purchased;

    private String comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(double purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
