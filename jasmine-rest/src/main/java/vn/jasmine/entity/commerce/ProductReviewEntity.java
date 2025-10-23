package vn.jasmine.entity.commerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_REVIEW")
public class ProductReviewEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "product_id", columnDefinition = "UUID")
    private UUID productId;

    @Column(name = "rating")
    private float rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now();
    }

}
