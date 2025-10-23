package vn.jasmine.entity.commerce;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_IMAGE")
public class ProductImageEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageURL;

    @JoinColumn(name = "product_id", columnDefinition = "UUID")
    private UUID productId;

}
