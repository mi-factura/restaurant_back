package software.restaurante.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.restaurante.domain.ConsumableCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumableCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long restaurantId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<ConsumableDTO> consumables;

    public static ConsumableCategoryDTO fromEntity(ConsumableCategory category) {
        if (category == null) {
            return null;
        }

        return ConsumableCategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .restaurantId(category.getRestaurant() != null ? category.getRestaurant().getId() : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .consumables(category.getConsumables() != null ? 
                    category.getConsumables().stream()
                        .map(ConsumableDTO::fromEntity)
                        .collect(Collectors.toList()) : 
                    null)
                .build();
    }

    public static List<ConsumableCategoryDTO> fromEntities(List<ConsumableCategory> categories) {
        if (categories == null) {
            return List.of();
        }
        return categories.stream()
                .map(ConsumableCategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
