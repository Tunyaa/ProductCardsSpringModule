package module.ProductCardsSpringModule.DTO;

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

}
