package software.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import software.restaurante.domain.Order;
import software.restaurante.utils.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT o FROM Order o 
        JOIN FETCH o.table
        WHERE o.restaurant.id = :restaurantId
        AND (:sellerId IS NULL OR o.seller.id = :sellerId)
        AND (:statuses IS NULL OR o.status IN (:statuses))
    """)
    List<Order> findByRestaurantIdAndOptionalFilterSellerId(
            @Param("restaurantId") Long restaurantId,
            @Param("sellerId") UUID sellerId,
            @Param("statuses") List<OrderStatus> statuses
    );

    List<Order> findByRestaurantId(Long restaurantId);

}
