package software.restaurante.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Restaurant;
import software.restaurante.domain.Table;
import software.restaurante.domain.Zone;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {



    List<Table> findByRestaurantId(Long restaurantId);

    Table findByIdAndRestaurantId(Long restaurantId, Long tableId);

    @Query("""
        SELECT t FROM Table t
        WHERE t.restaurant.id = :restaurantId
        AND (:zoneId IS NULL OR t.zone.id = :zoneId)
        """)
    List<Table> findByRestaurantIdAndZoneId(Long restaurantId, Long zoneId);

}
