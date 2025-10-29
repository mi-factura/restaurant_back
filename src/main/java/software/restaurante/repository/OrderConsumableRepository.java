package software.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Consumable;
import software.restaurante.domain.OrderConsumable;

import java.util.List;

@Repository
public interface OrderConsumableRepository extends JpaRepository<OrderConsumable, Long> {


}
