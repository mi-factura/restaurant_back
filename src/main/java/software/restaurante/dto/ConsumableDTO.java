package software.restaurante.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.restaurante.domain.Consumable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumableDTO {
    private Long id;
    private String name;
    private String description;
    private String sku;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
    private Long restaurantId;
    private Double unitPrice;
    private String currency;
    private Integer stockQuantity;
    private Integer minStockLevel;
    private Boolean isActive;
    private JsonNode timeSlots;
    private JsonNode ingredients;
    private JsonNode nutritionalInfo;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static ConsumableDTO fromEntity(Consumable consumable) {
        if (consumable == null) {
            return null;
        }

        return ConsumableDTO.builder()
                .id(consumable.getId())
                .name(consumable.getName())
                .description(consumable.getDescription())
                .sku(consumable.getSku())
                .imageUrl(consumable.getImageUrl())
                .categoryId(consumable.getCategory() != null ? consumable.getCategory().getId() : null)
                .categoryName(consumable.getCategory() != null ? consumable.getCategory().getName() : null)
                .restaurantId(consumable.getRestaurant() != null ? consumable.getRestaurant().getId() : null)
                .unitPrice(consumable.getUnitPrice())
                .currency(consumable.getCurrency())
                .stockQuantity(consumable.getStockQuantity())
                .minStockLevel(consumable.getMinStockLevel())
                .isActive(consumable.getIsActive())
                .timeSlots(consumable.getTimeSlots())
                .ingredients(consumable.getIngredients())
                .nutritionalInfo(consumable.getNutritionalInfo())
                .createdAt(consumable.getCreatedAt())
                .updatedAt(consumable.getUpdatedAt())
                .build();
    }

    public static List<ConsumableDTO> fromEntities(List<Consumable> consumables) {
        if (consumables == null) {
            return List.of();
        }
        return consumables.stream()
                .map(ConsumableDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
