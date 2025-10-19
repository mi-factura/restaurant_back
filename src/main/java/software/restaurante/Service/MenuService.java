package software.restaurante.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.restaurante.domain.ConsumableCategory;
import software.restaurante.dto.ConsumableCategoryDTO;
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

  private final ConsumableCategoryRepository consumableCategoryRepository;

  public List<ConsumableCategoryDTO> getCategoriesByRestaurant(long restaurantId, Long categoryId, String searchParam) {
    validateUserRestaurant(restaurantId);
    List<ConsumableCategory> categories = consumableCategoryRepository.findWithFilters(restaurantId, categoryId, searchParam);
    return ConsumableCategoryDTO.fromEntities(categories);
  }




  private static void validateUserRestaurant(long restaurantId) {
    boolean isValidUserRestaurant = RoleValidator.validateAnyRole(RoleType.getRoleTypes(), restaurantId);

    if (!isValidUserRestaurant) {
      throw new ForbiddenException("User dont have permission to access this resource",ErrorCode.FORBIDDEN);
    }
  }



}
