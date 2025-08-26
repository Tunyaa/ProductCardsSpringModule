package module.ProductCardsSpringModule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.lang.Nullable;

/**
 *
 * @author tunyaa
 */
// Позиция - состовная часть заказа, стостоит из продукта,
// количества в кг или шт и для кого преобретается.
//
//
// Цикл жизни позиции: 
//В контроллере в методе get создаётся пустая позиция и передаётся в шаблон
//В шаблоне пользователь выбирает продукт, id продукта добавляется в поле  priductId позиции и
//возвращается из шаблона в контроллер метод post /add и далее в метод addPosition()
//В методе из БД по productId достаётся и присваевается продукт
//Генерируется явно id(UUID) позиции(Чтобы при удалении можно было найти позицию по id)
//(Позиции не сохраняются в БД до подтверждения заказа(Order))
//Далее позиция помещается в List и метод завершается редиректом на get /create
//List с позициями передаётся в шаблон
//В шаблоне выводится List позиций
//
//
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    // Используется EAGER так как все продукты загружаются в одном запросе и будут выведены все в списке.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "product_id")
    private Product product;

    private double quantity;

    private String consumer;

    @Column(precision = 19, scale = 0)
    private BigDecimal buyingPrice;
    
    @Column(nullable = true) // не обязательно, по умолчанию true
    private double purchasedQuantity;

    @Column(precision = 19, scale = 0)
    private BigDecimal purchaseAmount;

    @Column(nullable = true) // не обязательно, по умолчанию true
    private Boolean purchased = false;

    private String comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    

}
