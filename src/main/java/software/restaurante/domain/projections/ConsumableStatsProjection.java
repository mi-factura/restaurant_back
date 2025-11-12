package software.restaurante.domain.projections;

import java.math.BigDecimal;

public interface ConsumableStatsProjection {

    Long getConsumableId();
    String getConsumableName();
    Integer getQuantity();
    BigDecimal getTotalSailed();
}
