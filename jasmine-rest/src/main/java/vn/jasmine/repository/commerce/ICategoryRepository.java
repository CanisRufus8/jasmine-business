package vn.jasmine.repository.commerce;

import vn.jasmine.entity.commerce.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    CategoryEntity findByCategoryCode(String code);

}
