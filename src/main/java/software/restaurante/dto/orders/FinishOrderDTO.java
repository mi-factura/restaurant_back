package software.restaurante.dto.orders;

import lombok.Getter;
import software.restaurante.utils.enums.PaymentMethod;

import javax.validation.constraints.NotNull;

@Getter
public class FinishOrderDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private Long restaurantId;

    @NotNull
    private PaymentMethod paymentMethod;

}
