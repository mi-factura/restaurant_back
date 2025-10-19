package software.restaurante.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Restaurant;
import software.restaurante.domain.Table;
import software.restaurante.domain.Zone;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {



    List<Table> findByRestaurantId(Long restaurantId);

    List<Table> findByRestaurantIdAndZoneId(Long restaurantId, Long zoneId);

}
