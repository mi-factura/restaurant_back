package software.restaurante.utils.enums;

import java.util.List;

public enum RoleType {
  WAITER,
  ADMIN,
  OWNER,
  CASHIER;

  public static List<RoleType> ALL_ROLE_TYPES() {
    return List.of(RoleType.ADMIN, RoleType.WAITER, RoleType.OWNER, RoleType.CASHIER);
  }

  public static List<RoleType> ADMIN_OWNER_CASHIER_ROLE_TYPES() {
      return List.of(RoleType.ADMIN, RoleType.OWNER, RoleType.CASHIER);
  }
}
