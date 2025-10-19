package software.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {


}
