package software.restaurante.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Restaurant;
import software.restaurante.domain.User;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


}
