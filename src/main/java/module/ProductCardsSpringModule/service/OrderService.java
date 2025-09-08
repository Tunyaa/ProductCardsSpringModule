package module.ProductCardsSpringModule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.repository.OrderRepository;
import module.ProductCardsSpringModule.repository.ProductRepository;
import module.ProductCardsSpringModule.service.aop.Monitor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tunyaa
 */
@Service
@Scope("prototype")
public class OrderService {

    private List<PositionDTO> positions;  // Список текущих позиций
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.positions = new ArrayList<>();
    }

    @Transactional
    public void createOrder() {
        if (getPositions().size() > 0) {
            // Создается новый заказ, куда помещяется текущий список позиций и сохраняется в БД
            Order order = new Order();
            for (PositionDTO positionDto : getPositions()) {
                // Создаёт позицию на основе DTO
                Position position = new Position();
                position.setConsumer(positionDto.getConsumer());
                position.setProduct(positionDto.getProduct());
                position.setQuantity(positionDto.getQuantity());

                // Добавляет позицию в список позиций в заказе
                order.getPositions().add(position);
            }

            // Присваивает полю заказа текущую дату и время
            order.setCreatedAt(LocalDateTime.now());

            // Сохраняет заказ в БД
            orderRepository.save(order);
        }
    }

    // Возвращает список заказов
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        Collections.reverse(orders);
        return orders;
    }

    // Удаляет заказ по id
    public void deleteOrderByID(Long id) {
        orderRepository.deleteById(id);
    }

    // Добавляет позицию в список
    @Monitor
    public void addPosition(PositionDTO position) {
        // Загружает продукт из БД по id. Присваивает продукт позиции
        position.setProduct(productRepository.findById(position.getProductId()).orElseThrow());
        // Присваивает id позиции. Используется в deletePosition() - удалить позицию из списка по id
        position.setId(UUID.randomUUID());
        // Добавляет позицию в список
        positions.add(position);
    }

    // Удаляет позицию из списка по id
    public void deletePosition(PositionDTO position) {
        positions.removeIf(e -> e.getId().equals(position.getId()));
    }

    // Возвращает список позиций по id заказа
    public void getPositionsByOrderId(Long id) {

        // Загружает заказ по id из БД
        Order order = orderRepository.findById(id).orElseThrow();

        setPositions(positionsToPositionsDTO(order.getPositions()));

    }

    // Преобразовывает список position  в positionDTO, сохраняет в список
    public List<PositionDTO> positionsToPositionsDTO(List<Position> positions) {
        List<PositionDTO> positionsDTO = new ArrayList<>();
        for (Position position : positions) {

            PositionDTO positionDTO = new PositionDTO();
            // Создаёт DTO на основе позиции из списка позиций заказа
            positionDTO.setId(position.getId());
            positionDTO.setProduct(position.getProduct());
            positionDTO.setProductId(positionDTO.getProduct().getId());
            positionDTO.setQuantity(position.getQuantity());
            positionDTO.setConsumer(position.getConsumer());
            positionDTO.setBuyingPrice(position.getBuyingPrice());
            positionDTO.setPurchasedQuantity(position.getPurchasedQuantity());
            positionDTO.setPurchaseAmount(position.getPurchaseAmount());
            positionDTO.setPurchased(position.isPurchased());
            positionDTO.setComment(position.getComment());
            // Добавляет DTO в список
            positionsDTO.add(positionDTO);

        }
        return positionsDTO;
    }

    // Очищает List позиций
    public void clearPositions() {
        positions.clear();
    }

    public List<PositionDTO> getPositions() {
        return positions;
    }

    private void setPositions(List<PositionDTO> positions) {
        this.positions = positions;
    }

}
