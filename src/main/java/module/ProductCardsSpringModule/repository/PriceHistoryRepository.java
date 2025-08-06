package module.ProductCardsSpringModule.repository;

import java.util.List;
import module.ProductCardsSpringModule.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    public List<PriceHistory> findAllByProductId(Long id);

}
