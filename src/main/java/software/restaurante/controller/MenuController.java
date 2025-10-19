package software.restaurante.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.restaurante.Service.MenuService;
import software.restaurante.dto.ConsumableCategoryDTO;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu")
    public ResponseEntity<List<ConsumableCategoryDTO>> getCategories(
        @RequestParam() Long restaurantId,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String searchParam) {
        return ResponseEntity.ok(menuService.getCategoriesByRestaurant(restaurantId, categoryId, searchParam));
    }
}
