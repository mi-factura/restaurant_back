package software.restaurante.dto.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.restaurante.domain.Order;
import software.restaurante.domain.OrderConsumable;
import software.restaurante.dto.TableDTO;
import software.restaurante.dto.UserDTO;
import software.restaurante.utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {

    private Long id;

    private TableDTO table;

    private UserDTO seller;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private BigDecimal iva;

    private BigDecimal inc;

    private List<OrderConsumableResponseDTO> orderConsumables;

    public static OrderResponseDTO fromEntity(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .table(TableDTO.builder()
                        .id(order.getTable().getId())
                        .name(order.getTable().getName())
                        .zoneId(order.getTable().getZone().getId())
                        .capacity(order.getTable().getCapacity())
                        .build())
                .seller(UserDTO.createBasicInfo(order.getSeller()))
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .iva(order.getIva())
                .inc(order.getInc())
                .build();
    }

    public static OrderResponseDTO fromEntity(Order order, List<OrderConsumable> orderConsumables) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .table(TableDTO.builder()
                        .id(order.getTable().getId())
                        .name(order.getTable().getName())
                        .zoneId(order.getTable().getZone().getId())
                        .build())
                .seller(UserDTO.createBasicInfo(order.getSeller()))
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .inc(order.getInc())
                .orderConsumables(orderConsumables.stream().map(
                        OrderConsumableResponseDTO::fromEntity
                ).toList())
                .build();
    }



}
