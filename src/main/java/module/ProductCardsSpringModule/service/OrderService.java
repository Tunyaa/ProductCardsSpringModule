package module.ProductCardsSpringModule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import module.ProductCardsSpringModule.DTO.PositionDTO;
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

    private List<PositionDTO> positions = new ArrayList<>();
    public final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PositionRepository positionRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.positionRepository = positionRepository;
    }

    public void addPosition(PositionDTO position) {

        position.setProduct(productRepository.findById(position.getProductId()).orElseThrow());

        position.setId(UUID.randomUUID());

        positions.add(position);

    }

    public List<PositionDTO> getAllPositions() {
        return positions;
    }

    public void clearPositions() {
        positions.clear();
    }

    @Transactional
    public void createOrder() {
        // Создается новый заказ, куда помещяется текущий список позиций и сохраняется в БД
        Order order = new Order();
        for (PositionDTO positionDto : positions) {

            Position position = new Position();
            position.setConsumer(positionDto.getConsumer());
            position.setProduct(positionDto.getProduct());
            position.setQuantity(positionDto.getQuantity());

            order.getPositions().add(position);
        }
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
        // Сприсок очищается
        clearPositions();
    }

    public void deletePosition(PositionDTO position) {

        positions.removeIf(e -> e.getId().equals(position.getId()));

    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    public List<PositionDTO> getPositionsByOrderId(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        clearPositions();
        for (Position position : order.getPositions()) {
            PositionDTO positionDTO = new PositionDTO();

            positionDTO.setId(UUID.randomUUID());
            positionDTO.setProduct(position.getProduct());
            positionDTO.setProductId(positionDTO.getProduct().getId());
            positionDTO.setQuantity(position.getQuantity());
            positionDTO.setConsumer(position.getConsumer());

            positions.add(positionDTO);
        }

        for (PositionDTO position : positions) {
            System.out.println(position.getProduct().getName());
        }
        return positions;
    }

    public void deleteOrderByID(Long id) {
        orderRepository.deleteById(id);
    }

}
