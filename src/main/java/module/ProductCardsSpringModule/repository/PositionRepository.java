package module.ProductCardsSpringModule.repository;

import java.util.UUID;
import module.ProductCardsSpringModule.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface PositionRepository  extends JpaRepository<Position, UUID>{
    
}
