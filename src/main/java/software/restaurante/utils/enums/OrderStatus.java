package software.restaurante.utils.enums;

import java.util.List;

public enum OrderStatus {
    PENDING,
    //IN_PROGRESS,
    //READY,
    DELIVERED,
    FINISHED,
    CANCELLED;

    public static List<OrderStatus> getOpenStatuses() {
        return List.of(PENDING, DELIVERED);
    };

    public static List<OrderStatus> getClosedStatuses() {
        return List.of(CANCELLED, FINISHED);
    };
}
