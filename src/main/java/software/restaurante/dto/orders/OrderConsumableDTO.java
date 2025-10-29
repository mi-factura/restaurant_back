package software.restaurante.dto.orders;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class OrderConsumableDTO {

    @NotNull
    private Long consumableId;

    @Min(1)
    private int quantity;

}
