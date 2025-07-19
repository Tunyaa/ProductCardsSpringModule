package module.ProductCardsSpringModule.service;

import java.util.ArrayList;
import java.util.List;
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
        positions.add(position);

    }

    public List<Position> getAllPositions() {
        return positions;
    }

}
