package module.ProductCardsSpringModule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.repository.OrderRepository;
import module.ProductCardsSpringModule.repository.PositionRepository;
import module.ProductCardsSpringModule.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tunyaa
 */
@Service
public class OrderService {

    private List<Position> positions = new ArrayList<>();
    public final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PositionRepository positionRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.positionRepository = positionRepository;
    }

    public void addPosition(Position position) {

        position.setProduct(productRepository.findById(position.getProductId()).orElseThrow());

        position.setId(UUID.randomUUID());

        positions.add(position);

    }

    public List<Position> getAllPositions() {
        return positions;
    }

    public void clearPositions() {
        positions.clear();
    }
@Transactional
    public void createOrder() {
        // Создается новый заказ, куда помещяется текущий список позиций и сохраняется в БД
        Order order = new Order();
        for (Position position : positions) {
//            positionRepository.save(position);
            order.getPositions().add(position);
        }
//        order.setPositions(positions);
        orderRepository.save(order);
        // Сприсок очищается
        clearPositions();
    }

    public void deletePosition(Position position) {

        positions.removeIf(e -> e.getId().equals(position.getId()));

    }

}
