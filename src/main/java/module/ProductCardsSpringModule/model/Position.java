package module.ProductCardsSpringModule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 *
 * @author tunyaa
 */
// Позиция - состовная часть заказа, стостоит из продукта,
// количества в кг или шт и для кого преобретается.
@Entity
@Table(name = "positions")
public class Position {

    @Id    
    @GeneratedValue(strategy =  GenerationType.UUID)
    public UUID id;

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_id")
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
