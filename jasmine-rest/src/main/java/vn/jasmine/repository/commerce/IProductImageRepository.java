package vn.jasmine.repository.commerce;

import vn.jasmine.entity.commerce.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {

    @Modifying
    @Query(value = "DELETE from product_image pi WHERE pi.product_id = :productId", nativeQuery = true)
    void clearImagesOfProduct(@Param("productId") UUID productId);

    List<ProductImageEntity> findAllByProductId(UUID productId);

}
