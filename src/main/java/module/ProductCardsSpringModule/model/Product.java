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

    private String variety; // Сорт (Черный Аватар, Гренни Смит, Персик плоский...)

    private String img; //Фотография продукта(ссылка на файл в папке)

    private String packing; // Виды фасовки(коробка 5кг, лотки по 600гр, мешок 40кг)

    private String whereBuy; // Номер павильёна или дока(4 док, 2/23 док, павильён 14/68)

    private String description; // Описание или цель применения(Для еды, кльян, декор, премиум)

    private String category; // Категория товара (Овощ, фрукт, зелень, орехи...)

    private String сountry; // Страна производитель

    private String seasonality; // Сезонность(Июль - Сентябрь, Октябрь - Февраль)

    @Column(precision = 19, scale = 2)
    private BigDecimal currentPrice; // Последняя цена покупки данного товара

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private ArrayList<PriceHistory> priceHistory = new ArrayList<>(); // Список изменения цены на конкретный продукт

    // Метод для обновления цены и сохранением старой цены в историю
    private void updatePrice(BigDecimal newPrice) {
        this.priceHistory.add(new PriceHistory(this, this.currentPrice)); // Создаём нувую запись цены на продукт

        currentPrice = newPrice; // Ставим актуальную цену на продукт
    }
}
