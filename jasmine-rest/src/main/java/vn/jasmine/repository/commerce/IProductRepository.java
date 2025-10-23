package vn.jasmine.repository.commerce;

import vn.jasmine.entity.commerce.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface IProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    @Modifying
    @Query("UPDATE ProductEntity p SET p.activeFlg = 1 where p.id =:entityId")
    void deleteById(UUID entityId);

    @Query("SELECT p FROM ProductEntity p WHERE p.activeFlg = 0")
    Page<ProductEntity> findAllByActiveFlg(Pageable pageable);

    Page<ProductEntity> findAll(Specification<ProductEntity> spec, Pageable pageable);

}
