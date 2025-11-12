package software.restaurante.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import software.restaurante.domain.Order;
import software.restaurante.domain.projections.ConsumableStatsProjection;
import software.restaurante.domain.projections.OrdersBySellerResume;
import software.restaurante.dto.orders.OrderResponseDTO;
import software.restaurante.repository.OrderConsumableRepository;
import software.restaurante.repository.OrderRepository;
import software.restaurante.utils.RoleValidator;
import software.restaurante.utils.enums.RoleType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessDataService {

    private final OrderRepository orderRepository;

    private final OrderConsumableRepository orderConsumableRepository;

    public List<OrderResponseDTO> getOrdersStats(Long restaurantId, LocalDate startDate, LocalDate endDate, int page, int size) {

        RoleValidator.validateUserRestaurant(restaurantId, RoleType.ADMIN_OWNER_ROLE_TYPES());

        OffsetDateTime start = startDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();
        OffsetDateTime end = endDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();

        Pageable pageable = PageRequest.of(page, size);
        List<Order> order = orderRepository.findByRestaurantIdAndDateCreatedRange(restaurantId, start, end, pageable);

        return order.stream().map(OrderResponseDTO::fromEntity).collect(java.util.stream.Collectors.toList());
    }

    public List<OrdersBySellerResume> getOrdersStatsByWaiter(Long restaurantId, LocalDate startDate, LocalDate endDate) {

        RoleValidator.validateUserRestaurant(restaurantId, RoleType.ADMIN_OWNER_ROLE_TYPES());

        OffsetDateTime start = startDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();
        OffsetDateTime end = endDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();


        return orderRepository.getOrderBySellerResumeBySeller(restaurantId, start, end);
    }

    public List<ConsumableStatsProjection> getConsumablesStats(Long restaurantId, LocalDate startDate, LocalDate endDate) {

        RoleValidator.validateUserRestaurant(restaurantId, RoleType.ADMIN_OWNER_ROLE_TYPES());

        OffsetDateTime start = startDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();
        OffsetDateTime end = endDate.atStartOfDay(ZoneId.of("America/Bogota")).toOffsetDateTime();


        return orderConsumableRepository.getConsumableStatsProjection(restaurantId, start, end);
    }


}
