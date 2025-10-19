package software.restaurante.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.stereotype.Service;
import software.restaurante.domain.Consumable;
import software.restaurante.domain.ConsumableCategory;
import software.restaurante.domain.Zone;
import software.restaurante.dto.ConsumableCategoryDTO;
import software.restaurante.dto.ConsumableDTO;
import software.restaurante.dto.ZoneDTO;
import software.restaurante.execptions.DataNotFoundException;
import software.restaurante.execptions.ForbiddenException;
import software.restaurante.repository.ConsumableCategoryRepository;
import software.restaurante.repository.ConsumableRepository;
import software.restaurante.utils.RoleValidator;
import software.restaurante.utils.enums.ErrorCode;
import software.restaurante.utils.enums.RoleType;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final ConsumableRepository consumableRepository;

    private final ConsumableCategoryRepository consumableCategoryRepository;
    private final ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder;

    public List<ConsumableCategoryDTO> getCategoriesByRestaurant(long restaurantId, Long categoryId, String searchParam) {
      validateUserRestaurant(restaurantId);
      List<ConsumableCategory> categories = consumableCategoryRepository.findWithFilters(restaurantId, categoryId, searchParam);
      return ConsumableCategoryDTO.fromEntities(categories);
    }

    public List<ConsumableDTO> getItemsByRestaurant(Long restaurantId, Long categoryId, String searchParam) {
        validateUserRestaurant(restaurantId);
        List<Consumable> consumbales = consumableRepository.findByRestaurantIdAndOptionalFilters(restaurantId, categoryId, searchParam);
        return ConsumableDTO.fromEntities(consumbales);
    }


    private static void validateUserRestaurant(long restaurantId) {
    boolean isValidUserRestaurant = RoleValidator.validateAnyRole(RoleType.getRoleTypes(), restaurantId);

    if (!isValidUserRestaurant) {
      throw new ForbiddenException("User dont have permission to access this resource",ErrorCode.FORBIDDEN);
    }
  }


    public ConsumableCategoryDTO getCategoryById(Long restaurantId, Long categoryId) {
        validateUserRestaurant(restaurantId);
        Optional<ConsumableCategory> category = consumableCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new DataNotFoundException("Item not found", ErrorCode.DATA_NOT_FOUND);
        }
        return ConsumableCategoryDTO.fromEntity(category.get());
    }

    public ConsumableDTO getItemById(Long restaurantId, Long itemId) {
        validateUserRestaurant(restaurantId);
        Optional<Consumable> consumable = consumableRepository.findById(itemId);
        if (consumable.isEmpty()) {
            throw new DataNotFoundException("Zone not found", ErrorCode.DATA_NOT_FOUND);
        }
        return ConsumableDTO.fromEntity(consumable.get());
    }
}
