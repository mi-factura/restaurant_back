package software.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

/**
 * Data Transfer Object for Zone entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO {
    private Long id;
    private String name;
    private String status;
    private String color;
    private String description;
    private Long restaurantId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    /**
     * Creates a ZoneDTO from a Zone entity
     * @param zone the Zone entity to convert
     * @return a new ZoneDTO instance
     */
    public static ZoneDTO fromEntity(software.restaurante.domain.Zone zone) {
        if (zone == null) {
            return null;
        }

        return ZoneDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .status(zone.getStatus())
                .color(zone.getColor())
                .description(zone.getDescription())
                .restaurantId(zone.getRestaurant() != null ? zone.getRestaurant().getId() : null)
                .createdAt(zone.getCreatedAt())
                .updatedAt(zone.getUpdatedAt())
                .build();
    }

}
