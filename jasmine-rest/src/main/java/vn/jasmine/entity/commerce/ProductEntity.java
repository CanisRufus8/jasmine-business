package vn.jasmine.entity.commerce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductEntity implements Persistable<UUID> {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "product_code", columnDefinition = "VARCHAR(20)")
    private String productCode;

    @Column(name = "product_name", columnDefinition = "VARCHAR(255)")
    private String productName;

    @Column(name = "unit", columnDefinition = "VARCHAR(100)")
    private String unit;

    @Column(name = "product_title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "main_image", columnDefinition = "TEXT")
    private String mainImage;

    @Column(name = "information", columnDefinition = "TEXT")
    private String information;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "wholesale_price")
    private Double wholesalePrice;

    @Column(name = "retail_price")
    private Double retailPrice;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "input_quantity")
    private Integer inputQuantity;

    @Column(name = "sold_quantity")
    private Integer soldQuantity;

    @Column(name = "remaining_quantity")
    private Integer remainingQuantity;

    @Column(name = "review_score")
    private Float reviewScore;

    @Column(name = "product_type_id", columnDefinition = "UUID")
    private UUID productTypeId;

    @Column(name = "category_id", columnDefinition = "UUID")
    private UUID categoryId;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;

    @Column(name = "active_flg")
    private Byte activeFlg;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
        this.activeFlg = 0;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

}
