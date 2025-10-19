package software.restaurante.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String nit;

    @Column(nullable = false, length = 1)
    private String dv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "legal_name", nullable = false, length = 255)
    private String legalName;

    @Column(name = "comercial_name", length = 255)
    private String comercialName;

    @Column(name = "person_type", nullable = false, length = 50)
    private String personType;

    @Column(name = "tax_regime", nullable = false, length = 100)
    private String taxRegime;

    @Column(name = "tax_responsibilities", length = 255)
    private String taxResponsibilities;

    @Column(name = "dian_resolution_id", nullable = false, length = 50)
    private String dianResolutionId;

    @Column(name = "dian_resolution_expedition", nullable = false)
    private OffsetDateTime dianResolutionExpedition;

    @Column(name = "dian_resolution_expiration", nullable = false)
    private OffsetDateTime dianResolutionExpiration;

    @Column(name = "dian_billing_range_start", nullable = false)
    private Integer dianBillingRangeStart;

    @Column(name = "dian_billing_range_end", nullable = false)
    private Integer dianBillingRangeEnd;

    @Column(name = "dian_billing_prefix", length = 10)
    private String dianBillingPrefix;

    private String dianEmail;

    @Column(name = "order_day_counter", nullable = false)
    private Integer orderDayCounter = 0;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Zone> zones;


// JPA requires a no-arg constructor
    protected Restaurant() {
    }

    public Restaurant(String nit, String dv, Address address, String legalName, String personType,
                     String taxRegime, String dianResolutionId, OffsetDateTime dianResolutionExpedition,
                     Integer dianBillingRangeEnd, String dianEmail) {
        this.nit = Objects.requireNonNull(nit, "NIT cannot be null");
        this.dv = Objects.requireNonNull(dv, "DV cannot be null");
        this.address = Objects.requireNonNull(address, "Address cannot be null");
        this.legalName = Objects.requireNonNull(legalName, "Legal name cannot be null");
        this.personType = Objects.requireNonNull(personType, "Person type cannot be null");
        this.taxRegime = Objects.requireNonNull(taxRegime, "Tax regime cannot be null");
        this.dianResolutionId = Objects.requireNonNull(dianResolutionId, "DIAN resolution ID cannot be null");
        this.dianResolutionExpedition = Objects.requireNonNull(dianResolutionExpedition, "DIAN resolution expedition date cannot be null");
        this.dianResolutionExpiration = Objects.requireNonNull(dianResolutionExpiration, "DIAN resolution expiration date cannot be null");
        this.dianBillingRangeStart = Objects.requireNonNull(dianBillingRangeStart, "DIAN billing range start cannot be null");
        this.dianBillingRangeEnd = Objects.requireNonNull(dianBillingRangeEnd, "DIAN billing range end cannot be null");
        this.dianEmail = Objects.requireNonNull(dianEmail, "DIAN email cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Business methods
    public void incrementOrderDayCounter() {
        if (this.orderDayCounter == null) {
            this.orderDayCounter = 0;
        }
        this.orderDayCounter++;
    }

    public boolean isResolutionValid() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return !now.isBefore(dianResolutionExpedition) &&
               !now.isAfter(dianResolutionExpiration);
    }
}
