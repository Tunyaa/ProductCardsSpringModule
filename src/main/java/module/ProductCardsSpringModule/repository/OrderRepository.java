package module.ProductCardsSpringModule.repository;

import module.ProductCardsSpringModule.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
