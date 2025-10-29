package software.restaurante.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.restaurante.utils.enums.TableStatus;

import java.time.OffsetDateTime;

/**
 * Data Transfer Object for Table entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDTO {
    private Long id;
    private String name;
    private int capacity;
    private Long zoneId;
    private TableStatus status;
    private Long currentOrderId;
    private UserDTO responsable;
    private OffsetDateTime occupiedSince;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    /**
     * Creates a TableDTO from a Table entity
     * @param table the Table entity to convert
     * @return a new TableDTO instance
     */
    public static TableDTO fromEntity(software.restaurante.domain.Table table) {
        if (table == null) {
            return null;
        }

        return TableDTO.builder()
                .id(table.getId())
                .name(table.getName())
                .capacity(table.getCapacity())
                .zoneId(table.getZone() != null ? table.getZone().getId() : null)
                .status(table.getStatus())
                .currentOrderId(table.getCurrentOrder() != null ? table.getCurrentOrder().getId() : null)
                .responsable(UserDTO.createBasicInfo(table.getResponsable()))
                .occupiedSince(table.getOccupiedSince())
                .createdAt(table.getCreatedAt())
                .updatedAt(table.getUpdatedAt())
                .build();
    }
}
