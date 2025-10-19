package software.restaurante.utils.enums;

import java.util.List;

public enum RoleType {
  WAITER,
  ADMIN,
  OWNER,
  CASHIER;

  public static List<RoleType> getRoleTypes() {
    return List.of(RoleType.ADMIN, RoleType.WAITER, RoleType.OWNER, RoleType.CASHIER);
  }
}
