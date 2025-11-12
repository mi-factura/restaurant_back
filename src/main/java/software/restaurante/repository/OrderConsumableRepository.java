package software.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.OrderConsumable;
import software.restaurante.domain.projections.ConsumableStatsProjection;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderConsumableRepository extends JpaRepository<OrderConsumable, Long> {

    @Query("""
        SELECT  oc.consumable.id as consumableId,
                oc.consumable.name as consumableName,
                SUM(oc.quantity) as quantity,
                SUM(oc.totalPrice) as totalSailed
        FROM OrderConsumable oc
        WHERE oc.order.restaurant.id = :restaurantId
        AND oc.createdAt BETWEEN :startDate AND :endDate
        GROUP BY oc.consumable.id, oc.consumable.name
        order by quantity DESC
        """)
    List<ConsumableStatsProjection> getConsumableStatsProjection(Long restaurantId, OffsetDateTime startDate, OffsetDateTime endDate);


}
