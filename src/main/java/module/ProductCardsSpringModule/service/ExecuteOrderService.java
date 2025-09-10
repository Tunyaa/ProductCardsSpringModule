package module.ProductCardsSpringModule.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import module.ProductCardsSpringModule.DTO.PositionDTO;
import module.ProductCardsSpringModule.DTO.PurchaseAmountByConsumersDTO;
import module.ProductCardsSpringModule.model.Order;
import module.ProductCardsSpringModule.model.Position;
import module.ProductCardsSpringModule.repository.OrderRepository;
import module.ProductCardsSpringModule.repository.PositionRepository;
import module.ProductCardsSpringModule.service.aop.Monitor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author tunyaa
 */
@Service
@Scope("prototype")
public class ExecuteOrderService {

    private Long currentOrderId;               // id текущего заказа
    private String methodParametr;          // Аргумент для поиска, который был передан последним
    private String lastMethod;                    // Метод поиска, который был выполен последним
    private final OrderRepository orderRepository;
    private final PositionRepository positionRepository;

    public ExecuteOrderService(OrderRepository orderRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.positionRepository = positionRepository;
    }

    // Возвращает список позиций по id заказа
    public List<PositionDTO> getExecutePositionsByOrderId(Long id) {

        // Загружает заказ по id из БД, очищает список
        // Если id не передан, то загружается список по номеру из текущего заказа
        if (id == null) {
            id = getCurrentOrderId();
        }
        Order order = orderRepository.findById(id).orElseThrow();
        List<PositionDTO> positionsDTO = positionsToPositionsDTO(order.getPositions());

        setCurrentOrderId(id);
        return positionsDTO;
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

    // Принимает из формы аргументы, вычисляет недостающее поле, сохраняет.
    @Monitor
    public void updateExecutePosition(PositionDTO position) {
        // Поле getPurchasedQuantity обязательное поле, проверяется в шаблоне.
        // Принимает buyingPrice, вычисляет purchaseAmount
        if (position.getPurchaseAmount() == null && position.getBuyingPrice() != null) {
            BigDecimal purchaseAmount = position.getBuyingPrice()
                    .multiply(BigDecimal.valueOf(position.getPurchasedQuantity()));
            position.setPurchaseAmount(purchaseAmount);
        }// Принимает purchaseAmount, вычисляет buyingPrice
        else if (position.getBuyingPrice() == null && position.getPurchaseAmount() != null) {
            BigDecimal valueOf = BigDecimal.valueOf(position.getPurchasedQuantity());
            BigDecimal buyingPrice = position.getPurchaseAmount()
                    .divide(valueOf, 2, RoundingMode.HALF_UP);
            position.setBuyingPrice(buyingPrice);
        }

        updatePosition(position);
    }

    // Обновляет позицию в БД. Загружает позицию из БД, меняет поля, сохраняет в БД.
    // Позиция меняет статус на "куплено", сохраняет цену, количество и сумму
    private void updatePosition(PositionDTO positionDTO) {

        Position position = positionRepository.findById(positionDTO.getId()).orElseThrow();
        position.getProduct().setCurrentPrice(positionDTO.getBuyingPrice());
        position.setBuyingPrice(positionDTO.getBuyingPrice());
        position.setPurchasedQuantity(positionDTO.getPurchasedQuantity());
        position.setPurchaseAmount(positionDTO.getPurchaseAmount());
        position.setComment(positionDTO.getComment());
        position.setPurchased(true);

        positionRepository.save(position);

    }

    // Поиск позиций по категории продукта
    public List<PositionDTO> findPositionsByProductCategory(String category) {
        setLastRequest(category, "findPositionsByProductCategory");
        if (category.equals("allCategories")) {
            // Сделать возвращение всех позиций
            List<PositionDTO> positionsByOrderId = getExecutePositionsByOrderId(currentOrderId);
            return positionsByOrderId;
        } else {
            List<Position> positions = positionRepository
                    .findByOrderIdAndProductCategory(getCurrentOrderId(), category);
            List<PositionDTO> positionsDTO = positionsToPositionsDTO(positions);
            return positionsDTO;
        }
    }

    // Поиск позиций по переданной строке
    public List<PositionDTO> findPositionsByProductName(String productName) {
        setLastRequest(productName, "findPositionsByProductName");
        // Загружает полный список заказа
        List<PositionDTO> positions = getExecutePositionsByOrderId(currentOrderId);

        ArrayList<PositionDTO> positionsByName = new ArrayList<>();
        // Фильтрует позиции соответствующие запросу
        for (PositionDTO position : positions) {
            if (position.getProduct()
                    .getName()
                    .toLowerCase()
                    .matches(".*" + productName.toLowerCase() + ".*")
                    || position.getProduct()
                            .getVariety()
                            .toLowerCase()
                            .matches(".*" + productName.toLowerCase() + ".*")) {
                positionsByName.add(position);
            }
        }
        return positionsByName;
    }

    // Поиск позиций по статусу (куплен\некуплен)
    public List<PositionDTO> findPositionsByPurchaseStatus(String purchaseStatusString) {
        setLastRequest(purchaseStatusString, "findPositionsByPurchaseStatus");
        // Загружает полный список заказа
        List<PositionDTO> positions = getExecutePositionsByOrderId(currentOrderId);

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

    // Отменяет купленную позицию
    public void clearExicutePositionByID(UUID id) {
        // Находит позицию по id
        Position position = positionRepository.findById(id).orElseThrow();
        // Возвращает поля в исходное состояние(до покупки)
        position.setBuyingPrice(null);
        position.setPurchasedQuantity(0);
        position.setPurchaseAmount(null);
        position.setPurchased(false);
        // Сохраняет позицию
        positionRepository.save(position);
    }

    // Возвращает список позиций, по поиску, который был применен последним
    public List<PositionDTO> getPositionsByLastRequest() {
        List<PositionDTO> positions;
        if (this.methodParametr != null) {

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
                    positions = getExecutePositionsByOrderId(currentOrderId);
            }

        } else {
            positions = getExecutePositionsByOrderId(currentOrderId);
        }
        return positions;
    }

    // Присваивает аргумент и метод поиска, которые были выполнены последними
    private void setLastRequest(String methodParametr, String lastMethod) {
        this.lastMethod = lastMethod;
        this.methodParametr = methodParametr;
    }

    // Возвращает суммы заказа по потребителям
    public List<PurchaseAmountByConsumersDTO> purchaseAmountByConsumers() {

        List<PurchaseAmountByConsumersDTO> purchaseAmounts = new ArrayList<>();
        List<Object[]> list = positionRepository.findSumPurchaseAmountForCousumersByOrderId(currentOrderId);
        for (Object[] objects : list) {
            if ((BigDecimal) objects[1] != null) {
                purchaseAmounts.add(
                        new PurchaseAmountByConsumersDTO(
                                (String) objects[0],
                                (BigDecimal) objects[1]
                        ));
            }
        }
        return purchaseAmounts;
    }

    // Возвращает сумму заказа
    public BigDecimal purchaseAmount() {
        BigDecimal purchaseAmount = positionRepository.
                findSumPurchaseAmountByOrderId(currentOrderId);
        return purchaseAmount;
    }

    // Возвращает текущий id заказа
    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    // Присваивает id заказа
    public void setCurrentOrderId(Long currentOrderId) {
        this.currentOrderId = currentOrderId;
    }

}
