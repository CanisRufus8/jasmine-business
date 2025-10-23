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
@Table(name = "PRODUCT_TYPE")
public class ProductTypeEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "product_type_code", columnDefinition = "VARCHAR(20)")
    private String productTypeCode;

    @Column(name = "product_type_name", columnDefinition = "VARCHAR(100)")
    private String productTypeName;

}
