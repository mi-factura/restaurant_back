package software.restaurante.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.restaurante.service.ZoneService;
import software.restaurante.dto.TableDTO;
import software.restaurante.dto.ZoneDTO;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class ZonesController {

    private final ZoneService zoneService;


    @GetMapping("/zones/{restaurantId}")
    public ResponseEntity<List<ZoneDTO>> getZones(@PathVariable Long restaurantId) {

        return ResponseEntity.ok(zoneService.getZones(restaurantId));
    }

    @GetMapping("/tables")
    public ResponseEntity<List<TableDTO>> getTablesByRestaurant(
        @RequestParam Long restaurantId,
        @RequestParam(required=false) Long zoneId) {

        return ResponseEntity.ok(zoneService.getTables(restaurantId, zoneId));
    }
}
