package software.restaurante.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.restaurante.service.OrderService;
import software.restaurante.dto.orders.CreateOrderDTO;
import software.restaurante.dto.orders.FinishOrderDTO;
import software.restaurante.dto.orders.OrderResponseDTO;
import software.restaurante.utils.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("/open/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOpenOrders(
            @RequestParam Long restaurantId,
            @RequestParam(required = false) UUID userId) {
        List<OrderResponseDTO> orderResponseDTOs = orderService.getOpenOrdersByRestaurant(restaurantId, userId, OrderStatus.getOpenStatuses());

        return ResponseEntity.ok(orderResponseDTOs);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {

        OrderResponseDTO orderResponseDTO = orderService.createOrder(createOrderDTO);

        return ResponseEntity.ok(orderResponseDTO);

    }

    @PostMapping("/orders/finish")
    public ResponseEntity<OrderResponseDTO> finishOrder(@RequestBody FinishOrderDTO finishOrderDTO) {

        OrderResponseDTO orderResponseDTO = orderService.finishOrder(finishOrderDTO);

        return ResponseEntity.ok(orderResponseDTO);

    }

}
