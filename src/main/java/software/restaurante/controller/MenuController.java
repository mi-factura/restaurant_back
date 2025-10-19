package software.restaurante.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.restaurante.Service.MenuService;
import software.restaurante.domain.Consumable;
import software.restaurante.dto.ConsumableCategoryDTO;
import software.restaurante.dto.ConsumableDTO;

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

    @GetMapping("/items")
    public ResponseEntity<List<ConsumableDTO>> getItems(
            @RequestParam() Long restaurantId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String searchParam) {
        return ResponseEntity.ok(menuService.getItemsByRestaurant(restaurantId, categoryId, searchParam));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ConsumableCategoryDTO> getCategoryById(
            @RequestParam Long restaurantId,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(menuService.getCategoryById(restaurantId, categoryId));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ConsumableDTO> getItemById(
            @RequestParam Long restaurantId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(menuService.getItemById(restaurantId, itemId));
    }


}
