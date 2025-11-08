package software.restaurante.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.restaurante.domain.*;
import software.restaurante.dto.orders.CreateOrderDTO;
import software.restaurante.dto.orders.OrderResponseDTO;
import software.restaurante.execptions.BadRequestException;
import software.restaurante.execptions.DataConflictException;
import software.restaurante.execptions.DataNotFoundException;
import software.restaurante.repository.ConsumableRepository;
import software.restaurante.repository.OrderConsumableRepository;
import software.restaurante.repository.OrderRepository;
import software.restaurante.repository.TableRepository;
import software.restaurante.utils.RoleValidator;
import software.restaurante.utils.enums.ErrorCode;
import software.restaurante.utils.enums.OrderStatus;
import software.restaurante.utils.enums.RoleType;
import software.restaurante.utils.enums.TableStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static software.restaurante.utils.Constants.Tax.IVA;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final TableRepository tableRepository;

    private final ConsumableRepository consumableRepository;

    private final OrderConsumableRepository orderConsumableRepository;

    public List<OrderResponseDTO> getOpenOrdersByRestaurant(Long restaurantId, UUID userId, List<OrderStatus> statuses) {

        RoleValidator.validateUserRestaurant(restaurantId, RoleType.getRoleTypes());

        List<Order> orders = orderRepository.findByRestaurantIdAndOptionalFilterSellerId(restaurantId, userId, statuses);

        return orders.stream().map(OrderResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO) {

        RoleValidator.validateUserRestaurant(createOrderDTO.getRestaurantId(), RoleType.getRoleTypes());
        UUID currentUserId = RoleValidator.getUserId();

        Table table = getAndValidateTable(createOrderDTO);
        table.setStatus(TableStatus.OCCUPIED);
        table.setResponsable(User.withId(currentUserId));
        table.setOccupiedSince(OffsetDateTime.now(ZoneOffset.UTC));

        List<Consumable> consumables = consumableRepository.findByIdInAndRestaurantId(createOrderDTO.getOrderConsumablesIds(), createOrderDTO.getRestaurantId());

        List<OrderConsumable> orderConsumables = buildAndValidateConsumables(createOrderDTO, consumables);


        Order order = Order.builder()
                .orderNumber(123)
                .restaurant(Restaurant.withId(createOrderDTO.getRestaurantId()))
                .table(table)
                .seller(User.withId(currentUserId))
                .status(OrderStatus.PENDING)
                .totalAmount(getTotalAmountFromOrderConsumables(orderConsumables))
                .iva(IVA)
                .build();

        Order savedOrder = orderRepository.save(order);

        orderConsumables.forEach(orderConsumable -> orderConsumable.setOrder(savedOrder));
        List<OrderConsumable> savedOrderConsumables = orderConsumableRepository.saveAll(orderConsumables);


        return OrderResponseDTO.fromEntity(savedOrder, savedOrderConsumables);

    }

    private BigDecimal getTotalAmountFromOrderConsumables(List<OrderConsumable> orderConsumables) {
        return orderConsumables.stream()
                .map(e -> e.getUnitPrice().multiply(BigDecimal.valueOf(e.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Table getAndValidateTable(CreateOrderDTO createOrderDTO) {
        Table table = tableRepository.findByIdAndRestaurantId(createOrderDTO.getTableId(), createOrderDTO.getRestaurantId());
        if (table == null) {
            throw new DataNotFoundException("Table not found", ErrorCode.DATA_NOT_FOUND);
        }

        if (!TableStatus.AVAILABLE.equals(table.getStatus())) {
            throw new DataConflictException("Table is not available", ErrorCode.CONFLICT);
        }

        return table;
    }

    private List<OrderConsumable> buildAndValidateConsumables(CreateOrderDTO createOrderDTO, List<Consumable> consumables) {


        if (validateConsumablesIdsNotDuplicated(createOrderDTO)) {
            throw new BadRequestException("Duplicated consumables", ErrorCode.BAD_REQUEST);
        }

        if (createOrderDTO.getOrderConsumablesIds().size() != consumables.size()) {
            throw new DataConflictException("Some consumables are missing", ErrorCode.CONFLICT);
        }

        return consumables.stream().map(consumable -> {
            OrderConsumable orderConsumable = new OrderConsumable();
            orderConsumable.setConsumable(consumable);
            orderConsumable.setUnitPrice(consumable.getUnitPrice());
            int quantity = getQuantity(createOrderDTO, consumable);
            orderConsumable.setQuantity(quantity);
            orderConsumable.setTotalPrice(consumable.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
            return orderConsumable;

        }).collect(Collectors.toList());
    }

    private boolean validateConsumablesIdsNotDuplicated(CreateOrderDTO createOrderDTO) {
        return createOrderDTO.getOrderConsumablesIds().size() != createOrderDTO.getOrderConsumablesIds().stream().distinct().count();
    }

    private int getQuantity(CreateOrderDTO createOrderDTO, Consumable consumable) {
        return createOrderDTO.getOrderConsumables().stream().filter(orderConsumableDTO -> orderConsumableDTO.getConsumableId().equals(consumable.getId())).findFirst().get().getQuantity();
    }


}
