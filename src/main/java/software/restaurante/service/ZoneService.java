package software.restaurante.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.restaurante.domain.Restaurant;
import software.restaurante.domain.Table;
import software.restaurante.dto.TableDTO;
import software.restaurante.dto.ZoneDTO;
import software.restaurante.execptions.DataNotFoundException;
import software.restaurante.repository.RestaurantRepository;
import software.restaurante.repository.TableRepository;
import software.restaurante.repository.ZoneRepository;
import software.restaurante.utils.enums.ErrorCode;
import software.restaurante.utils.enums.RoleType;

import static software.restaurante.utils.RoleValidator.validateUserRestaurant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZoneService {

  private final RestaurantRepository restaurantRepository;

  private final ZoneRepository zoneRepository;

  private final TableRepository tableRepository;

  public List<ZoneDTO> getZones(long restaurantId) {

    validateUserRestaurant(restaurantId, RoleType.ALL_ROLE_TYPES());

    Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

    if (!restaurant.isPresent()) {
      log.error("Restaurant {} not found", restaurantId);
      throw new DataNotFoundException("Restaurant not found", ErrorCode.DATA_NOT_FOUND);
    }

    return restaurant.get().getZones().stream().map(ZoneDTO::fromEntity).collect(Collectors.toList());

  }

  public List<TableDTO> getTables(Long restaurantId, Long zoneId) {

    validateUserRestaurant(restaurantId, RoleType.ALL_ROLE_TYPES());

    return getTablesByZoneId(restaurantId, zoneId);
  }

  private List<TableDTO> getTablesByZoneId(Long restaurantId, Long zoneId) {

    List<Table> tables = tableRepository.findByRestaurantIdAndZoneId(restaurantId, zoneId);

    return tables.stream().map(TableDTO::fromEntity).collect(Collectors.toList());
  }

}
