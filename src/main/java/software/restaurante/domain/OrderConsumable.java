package software.restaurante.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "order_consumable")
@Getter
@Setter
public class OrderConsumable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumable_id", nullable = false)
    private Consumable consumable;

    @Column(name = "unit_price",nullable = false)
    private Double unitPrice;

    @Column(name="total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
