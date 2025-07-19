package module.ProductCardsSpringModule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.repository.OrderRepository;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author tunyaa
 */
@Service
public class OrderService {

    private List<Position> positions = new ArrayList<>();
    public final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void addPosition(Position position) {

        position.setProduct(productRepository.findById(position.getProductId()).orElseThrow());

        position.setId(UUID.randomUUID());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&& ADADADA");
        System.out.println(position.getId());
        positions.add(position);

    }

    public List<Position> getAllPositions() {
        return positions;
    }

    public void clearPositions() {
        positions.clear();
    }

    public void createOrder() {

    }

    public void deletePosition(Position position) {

        positions.removeIf(e -> e.getId().equals(position.getId()));

    }

}
