package software.restaurante.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import software.restaurante.utils.enums.RoleType;

public class RoleValidator {

  public static boolean validateAnyRole(List<RoleType> rolesToValidate, long restaurantId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Set<String> rolesWithRestaurant = rolesToValidate.stream()
        .map(role -> role.name() + "_" + restaurantId).collect(Collectors.toSet());

    return authentication.getAuthorities().stream().anyMatch(role -> rolesWithRestaurant.contains(role.getAuthority()));

  }

}
