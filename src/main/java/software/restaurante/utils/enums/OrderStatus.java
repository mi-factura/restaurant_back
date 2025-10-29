package software.restaurante.utils.enums;

import java.util.List;

public enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    READY,
    DELIVERED,
    CANCELLED,
    COMPLETED;

    public static List<OrderStatus> getOpenStatuses() {
        return List.of(PENDING, IN_PROGRESS, READY, DELIVERED);
    };

    public static List<OrderStatus> getClosedStatuses() {
        return List.of(CANCELLED, COMPLETED);
    };
}
