package software.restaurante.dto.orders;

import lombok.*;
import software.restaurante.domain.OrderConsumable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderConsumableResponseDTO {

    private Long consumableId;

    private String name;

    private Double unitPrice;

    private Integer quantity;

    public static OrderConsumableResponseDTO fromEntity(OrderConsumable entity){
        return OrderConsumableResponseDTO.builder()
                .consumableId(entity.getConsumable().getId())
                .name(entity.getConsumable().getName())
                .unitPrice(entity.getUnitPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
