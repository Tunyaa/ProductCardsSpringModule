package module.ProductCardsSpringModule.repository;

import java.util.Optional;
import java.util.UUID;
import module.ProductCardsSpringModule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tunyaa
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
