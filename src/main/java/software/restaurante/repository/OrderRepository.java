package software.restaurante.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import software.restaurante.domain.Order;
import software.restaurante.domain.projections.OrdersBySellerResume;
import software.restaurante.utils.enums.OrderStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

        @Query("""
            SELECT DISTINCT o FROM Order o
            JOIN FETCH o.table
            LEFT JOIN FETCH o.orderConsumables
            WHERE o.restaurant.id = :restaurantId
            AND (:sellerId IS NULL OR o.seller.id = :sellerId)
            AND (:statuses IS NULL OR o.status IN (:statuses))
        """)
        List<Order> findByRestaurantIdAndOptionalFilterSellerId(
                @Param("restaurantId") Long restaurantId,
                @Param("sellerId") UUID sellerId,
                @Param("statuses") List<OrderStatus> statuses
        );

    @Query("""
    
        SELECT o FROM Order o
        JOIN FETCH o.seller
        WHERE o.restaurant.id = :restaurantId
        AND o.createdAt BETWEEN :startDate AND :endDate
        order by o.createdAt DESC
    """)
    List<Order> findByRestaurantIdAndDateCreatedRange(
            Long restaurantId,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            Pageable page);


    @Query("""
        SELECT o.seller.id as sellerId,
             o.seller.firstName as sellerFirstName,
             o.seller.lastName AS sellerLastName,
             count(*) as ordersQuantity,
             sum(o.totalAmount) as totalAmount FROM Order o
        WHERE o.restaurant.id = :restaurantId
        AND o.createdAt BETWEEN :startDate AND :endDate
        group by o.seller.id, o.seller.firstName, o.seller.lastName
    """)
    List<OrdersBySellerResume> getOrderBySellerResumeBySeller(Long restaurantId, OffsetDateTime startDate, OffsetDateTime endDate);


    Order findByIdAndRestaurantId(Long id, Long restaurantId);

}
