package vn.jasmine.entity.commerce;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "CATEGORY")
public class CategoryEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "category_code", columnDefinition = "VARCHAR(20)")
    private String categoryCode;

    @Column(name = "category_name", columnDefinition = "VARCHAR(100)")
    private String categoryName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
