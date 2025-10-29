package software.restaurante.dto.orders;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
public class CreateOrderDTO {

    //@NotNull
    private Long restaurantId;

    //@NotNull
    private Long tableId;

    private UUID clientId;

    //@NotEmpty
    private List<OrderConsumableDTO> orderConsumables;

    public List<Long> getOrderConsumablesIds() {
        return orderConsumables.stream().map(OrderConsumableDTO::getConsumableId).toList();
    }

}
