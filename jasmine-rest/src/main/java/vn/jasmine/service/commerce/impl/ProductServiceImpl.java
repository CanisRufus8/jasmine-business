package vn.jasmine.service.commerce.impl;

import vn.jasmine.constants.JasmineMessageConst;
import vn.jasmine.dto.request.commerce.ProductRequestDto;
import vn.jasmine.dto.response.commerce.ProductResponseDto;
import vn.jasmine.entity.commerce.ProductEntity;
import vn.jasmine.exception.instance.GlobalServerException;
import vn.jasmine.exception.instance.ProductNotFoundException;
import vn.jasmine.mapper.commerce.IProductMapper;
import vn.jasmine.repository.commerce.IProductRepository;
import vn.jasmine.service.commerce.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductEntity> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Page<ProductResponseDto> getProductPage(String code, String name, Long categoryId, Long productTypeId, Integer activeFlg, String sortBy, Direction direction, Pageable pageable) {
        try {
            Specification<ProductEntity> spec = (root, query, builder) -> builder.conjunction();

            if (code != null && !code.isEmpty()) {
                spec = spec.and((root, query, builder) -> builder.like(root.get("code"), "%" + code + "%"));
            }

            if (name != null && !name.isEmpty()) {
                spec = spec.and((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
            }

            if (categoryId != null) {
                spec = spec.and((root, query, builder) -> builder.equal(root.get("categoryId"), categoryId));
            }

            if (productTypeId != null) {
                spec = spec.and((root, query, builder) -> builder.equal(root.get("productTypeId"), productTypeId));
            }

            if (activeFlg == 1) {
                spec = spec.and((root, query, builder) -> builder.equal(root.get("activeFlg"), 0));
            }

            if (activeFlg == 0) {
                spec = spec.and((root, query, builder) -> builder.equal(root.get("activeFlg"), 1));
            }

            Pageable pageableWithSort = pageable;
            if (sortBy != null && !sortBy.isEmpty()) {
                Sort sort = Sort.by(direction, sortBy);
                pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
            }
            return productRepository.findAll(spec, pageableWithSort).map(item -> productMapper.entityToDtoForList(item));

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalServerException(JasmineMessageConst.SERVER_ERROR);
        }

    }

    @Override
    public Page<ProductResponseDto> getProductPageUser(Pageable pageable) {
        try {
            return productRepository.findAllByActiveFlg(pageable).map(item -> productMapper.entityToDtoForList(item));
        } catch (Exception e) {
            throw new GlobalServerException(JasmineMessageConst.SERVER_ERROR);
        }

    }

    @Override
    public ProductEntity addNew(ProductRequestDto productDto) {
        ProductEntity product = modelMapper.map(productDto, ProductEntity.class);
        return productRepository.save(product);
    }

    @Override
    public ProductEntity update(ProductResponseDto productDto) {
        return productRepository.save(modelMapper.map(productDto, ProductEntity.class));
    }

    @Override
    public ProductResponseDto findById(UUID id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return modelMapper.map(productOptional.get(), ProductResponseDto.class);
        } else {
            throw new ProductNotFoundException(JasmineMessageConst.PRODUCT_NOT_FOUND);
        }

    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException(JasmineMessageConst.PRODUCT_NOT_FOUND);
        }

    }

}
