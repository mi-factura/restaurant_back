package software.restaurante.utils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import software.restaurante.execptions.ForbiddenException;
import software.restaurante.utils.enums.ErrorCode;
import software.restaurante.utils.enums.RoleType;

public class RoleValidator {

    public static boolean validateAnyRole(List<RoleType> rolesToValidate, long restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> rolesWithRestaurant = rolesToValidate.stream().map(role -> role.name() + "_" + restaurantId).collect(Collectors.toSet());

        return authentication.getAuthorities().stream().anyMatch(role -> rolesWithRestaurant.contains(role.getAuthority()));

    }

    public static void validateUserRestaurant(long restaurantId, List<RoleType> rolesToValidate) {
        boolean isValidUserRestaurant = RoleValidator.validateAnyRole(rolesToValidate, restaurantId);

        if (!isValidUserRestaurant) {
            throw new ForbiddenException("User dont have permission to access this resource", ErrorCode.FORBIDDEN);
        }
    }

    public static UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }

}
