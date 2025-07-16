package module.ProductCardsSpringModule.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tunyaa
 */
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Общее название продукта (Черешня, Киви, Морковь...)

    private String variety; // Сорт (Черный Аватар, Гренни Смит...)

    private String img; //Фотография продукта(ссылка на файл в папке)

    private String packing; // Виды фасовки(коробка 5кг, лотки по 600гр, мешок 40кг)

    private String whereBuy; // Номер павильёна или дока(4 док, 2/23 док, павильён 14/68)

    private String description; // Описание или цель применения(Для еды, кльян, декор, премиум)

    private String category; // Категория товара (Овощ, фрукт, зелень, орехи...)

    private String country; // Страна производитель

    private String seasonality; // Сезонность(Июль - Сентябрь, Октябрь - Февраль)

    @Column(precision = 19, scale = 2)
    private BigDecimal currentPrice; // Последняя цена покупки данного товара

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PriceHistory> priceHistory = new ArrayList<>(); // Список изменения цены на конкретный продукт

    // Метод для обновления цены и сохранением старой цены в историю
    private void updatePrice(BigDecimal newPrice) {
        this.priceHistory.add(new PriceHistory(this, this.currentPrice)); // Создаём нувую запись цены на продукт

        currentPrice = newPrice; // Ставим актуальную цену на продукт
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getWhereBuy() {
        return whereBuy;
    }

    public void setWhereBuy(String whereBuy) {
        this.whereBuy = whereBuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSeasonality() {
        return seasonality;
    }

    public void setSeasonality(String seasonality) {
        this.seasonality = seasonality;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
    }

}
