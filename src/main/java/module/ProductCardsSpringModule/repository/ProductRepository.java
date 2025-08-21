package module.ProductCardsSpringModule.repository;

import java.util.List;
import module.ProductCardsSpringModule.model.PriceHistory;
import module.ProductCardsSpringModule.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findProductsByCategory(String category);

}
