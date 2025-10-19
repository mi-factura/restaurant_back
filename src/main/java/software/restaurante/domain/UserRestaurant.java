package software.restaurante.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.restaurante.utils.enums.RoleType;

@Entity
@Getter
@Setter
@Table(name = "user_restaurant")
public class UserRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor for JPA
    protected UserRestaurant() {
    }

    public UserRestaurant(Restaurant restaurant, User user, RoleType role) {
        this.restaurant = Objects.requireNonNull(restaurant, "Restaurant cannot be null");
        this.user = Objects.requireNonNull(user, "User cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // PrePersist and PreUpdate callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
