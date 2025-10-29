package software.restaurante.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.restaurante.domain.Order;
import software.restaurante.domain.OrderConsumable;
import software.restaurante.dto.TableDTO;
import software.restaurante.dto.UserDTO;
import software.restaurante.utils.enums.OrderStatus;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private TableDTO table;

    private UserDTO seller;

    private OrderStatus status;

    private double totalAmount;

    private double iva;

    private List<OrderConsumableResponseDTO> orderConsumables;

    public static OrderResponseDTO fromEntity(Order order) {
        return OrderResponseDTO.builder()
                .table(TableDTO.builder()
                        .id(order.getTable().getId())
                        .name(order.getTable().getName())
                        .zoneId(order.getTable().getZone().getId())
                        .build())
                .seller(UserDTO.createBasicInfo(order.getSeller()))
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .iva(order.getIva())
                .build();
    }

    public static OrderResponseDTO fromEntity(Order order, List<OrderConsumable> orderConsumables) {
        return OrderResponseDTO.builder()
                .table(TableDTO.builder()
                        .id(order.getTable().getId())
                        .name(order.getTable().getName())
                        .zoneId(order.getTable().getZone().getId())
                        .build())
                .seller(UserDTO.createBasicInfo(order.getSeller()))
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .iva(order.getIva())
                .orderConsumables(orderConsumables.stream().map(
                        OrderConsumableResponseDTO::fromEntity
                ).toList())
                .build();
    }



}
