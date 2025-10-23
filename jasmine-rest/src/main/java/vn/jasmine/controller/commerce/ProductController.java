package vn.jasmine.controller.commerce;

import vn.jasmine.dto.both.commerce.CategoryDto;
import vn.jasmine.dto.both.commerce.ProductTypeDto;
import vn.jasmine.dto.request.commerce.ProductRequestDto;
import vn.jasmine.dto.response.commerce.ProductResponseDto;
import vn.jasmine.entity.commerce.ProductEntity;
import vn.jasmine.service.commerce.ICategoryService;
import vn.jasmine.service.commerce.IProductImageService;
import vn.jasmine.service.commerce.IProductService;
import vn.jasmine.service.commerce.IProductTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IProductTypeService productTypeService;
    private final IProductImageService productImageService;

    @GetMapping(value = "/admins")
    public Page<ProductResponseDto> showProductsAdmin(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(required = false, value = "activeFlg") Integer activeFlg,
            @RequestParam(required = false, value = "productCode") String code,
            @RequestParam(required = false, value = "productName") String productName,
            @RequestParam(required = false, value = "categoryId") Long categoryId,
            @RequestParam(required = false, value = "productTypeId") Long productTypeId,
            @RequestParam(required = false, value = "sortType") String sortType
    ) {
        String sortBy;
        Sort.Direction direction;
        if ("SALE-DESC".equals(sortType)) {
            sortBy = "soldQuantity";
            direction = Sort.Direction.DESC;
        } else if ("SALE-ASC".equals(sortType)) {
            sortBy = "remainingQuantity";
            direction = Sort.Direction.DESC;
        } else {
            sortBy = "id";
            direction = Sort.Direction.ASC;
        }
        Pageable pagination = PageRequest.of(page, pageSize);
        return productService.getProductPage(code, productName, categoryId, productTypeId, activeFlg, sortBy, direction, pagination);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users")
    public Page<ProductResponseDto> showProductsUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size) {

        Pageable pagination = PageRequest.of(page, size);
        return productService.getProductPageUser(pagination);

    }

    @PostMapping()
    public ProductEntity create(@RequestBody @Valid ProductRequestDto productDto) {
        ProductEntity product = productService.addNew(productDto);
        if (product != null) {
            productImageService.saveOrUpdate(product, productDto.getProductImages());
        }
        return product;
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<ProductResponseDto> showCategories(@PathVariable UUID id) {

        ProductResponseDto productDto = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);

    }

    @PutMapping(value = "/update")
    public ProductEntity update(@RequestBody ProductResponseDto productDto) {
        ProductEntity product = productService.update(productDto);
        if (product != null) {
            productImageService.saveOrUpdate(product, productDto.getProductImages());
        }
        return product;
    }

    @PutMapping(value = "/delete/{id}")
    public void delete(@PathVariable UUID id) {
        productService.deleteById(id);
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> showCategories() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/types")
    public List<ProductTypeDto> showProductType() {
        return productTypeService.findAll();
    }

}
