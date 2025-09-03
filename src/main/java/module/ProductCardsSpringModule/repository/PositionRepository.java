package module.ProductCardsSpringModule.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import module.ProductCardsSpringModule.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

    // Возвращает список позиций заказа, продукт которой соответствует переданной категории
    @Query(value = "SELECT positions.id, product_id, quantity, consumer, buying_price, purchased_quantity, purchase_amount, purchased, comment  FROM positions JOIN("
            + "SELECT product_id.id, product_id.product_id FROM product_id JOIN ("
            + "SELECT id FROM product WHERE category = :category) AS a "
            + "ON  a.id = product_id.product_id) AS b ON b.id = positions.id "
            + "WHERE order_id = :orderId ;",
            nativeQuery = true)
    List<Position> findByOrderIdAndProductCategory(
            @Param("orderId") Long orderId,
            @Param("category") String category
    );

    // Возвращает сумму заказа
    @Query(value = "SELECT SUM(purchase_amount) FROM positions WHERE order_id = :orderId ;",
            nativeQuery = true)
    BigDecimal findSumPurchaseAmountByOrderId(@Param("orderId") Long orderId);

}
