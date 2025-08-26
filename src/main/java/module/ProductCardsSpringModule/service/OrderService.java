package module.ProductCardsSpringModule.service;

import java.math.BigDecimal;
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

    private List<PositionDTO> positions;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PositionRepository positionRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.positionRepository = positionRepository;
        this.positions = new ArrayList<>();
    }

    // Добавляет позицию в список
    public void addPosition(PositionDTO position) {
        // Загружает продукт из БД по id. Присваивает продукт позиции
        position.setProduct(productRepository.findById(position.getProductId()).orElseThrow());
        // Присваивает id позиции. Используется в deletePosition() - удалить позицию из списка по id
        position.setId(UUID.randomUUID());
        // Добавляет позицию в список
        positions.add(position);

    }

    // Возвращает List позиций
    public List<PositionDTO> getAllPositions() {
        return positions;
    }

    // Очищает List позиций
    public void clearPositions() {
        positions.clear();
    }

    @Transactional
    public void createOrder() {
        // Создается новый заказ, куда помещяется текущий список позиций и сохраняется в БД
        Order order = new Order();
        for (PositionDTO positionDto : positions) {
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
        // Очищает List позиций
        clearPositions();
    }

    // Удаляет позицию из списка по id
    public void deletePosition(PositionDTO position) {
        positions.removeIf(e -> e.getId().equals(position.getId()));
    }

    // Возвращает список заказов
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    // Возвращает список позиций заказа по id заказа
    public List<PositionDTO> getPositionsByOrderId(Long id) {
        // Загружает заказ по id из БД, очищает список.
        Order order = orderRepository.findById(id).orElseThrow();
        clearPositions();

        for (Position position : order.getPositions()) {
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
            positions.add(positionDTO);
        }

        return positions;
    }

    // Удаляет заказ по id
    public void deleteOrderByID(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateExecutePosition(PositionDTO position) {
        System.out.println("1");
        if (position.getPurchaseAmount() == null) {
            BigDecimal multiply = position.getBuyingPrice().multiply(BigDecimal.valueOf(position.getPurchasedQuantity()));
            position.setPurchaseAmount(multiply);
        }
        System.out.println("2");
        updatePosition(position);
    }

    public void updatePosition(PositionDTO positionDTO) {
        System.out.println("3");
        Position position = positionRepository.findById(positionDTO.getId()).orElseThrow();
        System.out.println("4");
        position.setBuyingPrice(positionDTO.getBuyingPrice());
        position.setPurchasedQuantity(positionDTO.getPurchasedQuantity());
        position.setPurchaseAmount(positionDTO.getPurchaseAmount());
        position.setComment(positionDTO.getComment());
        position.setPurchased(true);
        System.out.println("5");
        positionRepository.save(position);

    }

}
