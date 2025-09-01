package module.ProductCardsSpringModule.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.model.Product;
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
    private Long currentOrderId;
    private String methodParametr;
    private String lastMethod;
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

    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(Long currentOrderId) {
        this.currentOrderId = currentOrderId;
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

        // Загружает заказ по id из БД, очищает список
        // Если id не передан, то загружается список по номеру из текущего заказа
        if (id == null) {
            id = getCurrentOrderId();
        }
        Order order = orderRepository.findById(id).orElseThrow();

        List<PositionDTO> positions = positionsToPositionsDTO(order.getPositions());

        setCurrentOrderId(id);
        return positions;
    }

    // Удаляет заказ по id
    public void deleteOrderByID(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateExecutePosition(PositionDTO position) {

        if (position.getPurchaseAmount() == null && position.getBuyingPrice() != null) {
            BigDecimal purchaseAmount = position.getBuyingPrice().multiply(BigDecimal.valueOf(position.getPurchasedQuantity()));
            position.setPurchaseAmount(purchaseAmount);
        } else if (position.getBuyingPrice() == null && position.getPurchaseAmount() != null) {
            BigDecimal valueOf = BigDecimal.valueOf(position.getPurchasedQuantity());
            BigDecimal buyingPrice = position.getPurchaseAmount().divide(valueOf, 2, RoundingMode.HALF_UP);
            position.setBuyingPrice(buyingPrice);
        }

        updatePosition(position);
    }

    public void updatePosition(PositionDTO positionDTO) {

        Position position = positionRepository.findById(positionDTO.getId()).orElseThrow();
        position.getProduct().setCurrentPrice(positionDTO.getBuyingPrice());
        position.setBuyingPrice(positionDTO.getBuyingPrice());
        position.setPurchasedQuantity(positionDTO.getPurchasedQuantity());
        position.setPurchaseAmount(positionDTO.getPurchaseAmount());
        position.setComment(positionDTO.getComment());
        position.setPurchased(true);

        positionRepository.save(position);

    }

    public List<PositionDTO> findPositionsByProductCategory(String category) {
        setLastRequest(category, "findPositionsByProductCategory");
        if (category.equals("allCategories")) {
            // Сделать возвращение всех позиций
            List<PositionDTO> positionsByOrderId = getPositionsByOrderId(currentOrderId);
            return positionsByOrderId;
        } else {
            List<Position> positions = positionRepository.findByOrderIdAndProductCategory(getCurrentOrderId(), category);
            List<PositionDTO> positionsToPositionsDTO = positionsToPositionsDTO(positions);
            return positionsToPositionsDTO;
        }

    }

    private List<PositionDTO> positionsToPositionsDTO(List<Position> positions) {
        clearPositions();
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
            this.positions.add(positionDTO);

        }
        return this.positions;
    }

    public List<PositionDTO> findPositionsByProductName(String productName) {
        setLastRequest(productName, "findPositionsByProductName");
        // Загружает полный список заказа
        List<PositionDTO> positions = getPositionsByOrderId(currentOrderId);

        ArrayList<PositionDTO> positionsByName = new ArrayList<>();
        // Фильтрует позиции соответствующие запросу
        for (PositionDTO position : positions) {

            if (position.getProduct().getName().toLowerCase().matches(".*" + productName.toLowerCase() + ".*")
                    || position.getProduct().getVariety().toLowerCase().matches(".*" + productName.toLowerCase() + ".*")) {
                positionsByName.add(position);
            }
        }

        for (PositionDTO positionDTO : positionsByName) {

        }

        return positionsByName;

    }

    public List<PositionDTO> findPositionsByPurchaseStatus(String purchaseStatusString) {
        setLastRequest(purchaseStatusString, "findPositionsByPurchaseStatus");
        // Загружает полный список заказа
        List<PositionDTO> positions = getPositionsByOrderId(currentOrderId);

        if (purchaseStatusString.equals("all")) {

            return positions;
        }
        List<PositionDTO> positionsByPurchaseStatus = new ArrayList<>();
        boolean purchaseStatusBoolean = purchaseStatusString.equals("purchased") ? true : false;
        // Фильтрует позиции соответствующие запросу
        for (PositionDTO position : positions) {

            if (position.getPurchased() == purchaseStatusBoolean) {

                positionsByPurchaseStatus.add(position);
            }
        }

        return positionsByPurchaseStatus;
    }

    public void clearExicutePositionByID(UUID id) {
        Position position = positionRepository.findById(id).orElseThrow();
        position.setBuyingPrice(null);
        position.setPurchasedQuantity(0);
        position.setPurchaseAmount(null);
        position.setPurchased(false);
        positionRepository.save(position);
    }

    public List<PositionDTO> getPositionsByLastRequest() {
        List<PositionDTO> positions;

        switch (this.lastMethod) {
            case "findPositionsByProductCategory":

                positions = findPositionsByProductCategory(this.methodParametr);
                break;
            case "findPositionsByProductName":

                positions = findPositionsByProductName(this.methodParametr);
                break;
            case "findPositionsByPurchaseStatus":

                positions = findPositionsByPurchaseStatus(this.methodParametr);
                break;

            default:
                throw new AssertionError();
        }
        return positions;
    }

    private void setLastRequest(String methodParametr, String lastMethod) {

        this.lastMethod = lastMethod;
        this.methodParametr = methodParametr;
    }

}
