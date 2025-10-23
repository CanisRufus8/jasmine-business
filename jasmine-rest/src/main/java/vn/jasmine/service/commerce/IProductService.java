package vn.jasmine.service.commerce;

import vn.jasmine.dto.request.commerce.ProductRequestDto;
import vn.jasmine.dto.response.commerce.ProductResponseDto;
import vn.jasmine.entity.commerce.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    List<ProductEntity> getProductList();

    Page<ProductResponseDto> getProductPage(String productCode, String productName, Long categoryId, Long productTypeId, Integer activeFlg, String sortBy, Direction direction, Pageable pageable);

    Page<ProductResponseDto> getProductPageUser(Pageable pageable);

    ProductEntity addNew(ProductRequestDto productDto);

    ProductEntity update(ProductResponseDto productDto);

    ProductResponseDto findById(UUID id);

    void deleteById(UUID id);
}
