package vn.jasmine.repository.commerce;

import vn.jasmine.entity.commerce.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductTypeRepository extends JpaRepository<ProductTypeEntity, UUID> {
}
