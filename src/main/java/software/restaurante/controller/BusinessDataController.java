package software.restaurante.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.restaurante.domain.projections.ConsumableStatsProjection;
import software.restaurante.domain.projections.OrdersBySellerResume;
import software.restaurante.service.BusinessDataService;
import software.restaurante.dto.orders.OrderResponseDTO;
import software.restaurante.execptions.BadRequestException;
import software.restaurante.utils.enums.ErrorCode;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/business")
public class BusinessDataController {

    private final  BusinessDataService businessDataService;

    @GetMapping("/stats/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersStats(
            @RequestParam Long restaurantId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam int page,
            @RequestParam int pageSize) {

            validateDates(startDate, endDate);

            return ResponseEntity.ok(businessDataService.getOrdersStats(restaurantId, startDate, endDate, page, pageSize));

    }

    @GetMapping("/stats/orders/by/seller")
    public ResponseEntity<List<OrdersBySellerResume>> getOrdersStatsBySeller(
            @RequestParam Long restaurantId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        validateDates(startDate, endDate);

        return ResponseEntity.ok(businessDataService.getOrdersStatsByWaiter(restaurantId, startDate, endDate));

    }

    @GetMapping("/stats/consumables")
    public ResponseEntity<List<ConsumableStatsProjection>> getConsumablesStatsBySeller(
            @RequestParam Long restaurantId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        validateDates(startDate, endDate);

        return ResponseEntity.ok(businessDataService.getConsumablesStats(restaurantId, startDate, endDate));

    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Invalid dates: startDate must be before endDate", ErrorCode.BAD_REQUEST);
        }

    }

}
