package vn.jasmine.mapper.commerce.impl;

import vn.jasmine.dto.both.commerce.CategoryDto;
import vn.jasmine.dto.both.commerce.ProductImageDto;
import vn.jasmine.dto.both.commerce.ProductTypeDto;
import vn.jasmine.dto.response.commerce.ProductResponseDto;
import vn.jasmine.entity.commerce.CategoryEntity;
import vn.jasmine.entity.commerce.ProductEntity;
import vn.jasmine.entity.commerce.ProductTypeEntity;
import vn.jasmine.mapper.commerce.IProductMapper;
import vn.jasmine.repository.commerce.ICategoryRepository;
import vn.jasmine.repository.commerce.IProductImageRepository;
import vn.jasmine.repository.commerce.IProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductMapperImpl implements IProductMapper {

    private final IProductTypeRepository productTypeRepository;
    private final ICategoryRepository categoryRepository;
    private final IProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto entityToFullDataDto(ProductEntity productEntity) {

        ProductResponseDto productResponseDto = new ProductResponseDto();

        // Set all basic property values
        productResponseDto.setId(productEntity.getId());
        productResponseDto.setProductCode(productEntity.getProductCode());
        productResponseDto.setProductName(productEntity.getProductName());
        productResponseDto.setUnit(productEntity.getUnit());
        productResponseDto.setTitle(productEntity.getTitle());
        productResponseDto.setMainImage(productEntity.getMainImage());
        productResponseDto.setInformation(productEntity.getInformation());
        productResponseDto.setDescription(productEntity.getDescription());
        productResponseDto.setNotes(productEntity.getNotes());
        productResponseDto.setWeight(productEntity.getWeight());
        productResponseDto.setInputQuantity(productEntity.getInputQuantity());
        productResponseDto.setCostPrice(productEntity.getCostPrice());
        productResponseDto.setWholesalePrice(productEntity.getWholesalePrice());
        productResponseDto.setRetailPrice(productEntity.getRetailPrice());
        productResponseDto.setDiscountPercent(productEntity.getDiscountPercent());
        productResponseDto.setSalePrice(productEntity.getSalePrice());
        productResponseDto.setSoldQuantity(productEntity.getSoldQuantity());
        productResponseDto.setRemainingQuantity(productEntity.getRemainingQuantity());
        productResponseDto.setReviewScore(productEntity.getReviewScore());

        // Set category data
        CategoryEntity categoryEntity = categoryRepository.findById(productEntity.getCategoryId()).orElse(null);
        CategoryDto categoryDto = modelMapper.map(categoryEntity, CategoryDto.class);
        productResponseDto.setCategory(categoryDto);

        // Set product type data
        ProductTypeEntity productTypeEntity = productTypeRepository.findById(productEntity.getProductTypeId()).orElse(null);
        ProductTypeDto productTypeDto = modelMapper.map(productTypeEntity, ProductTypeDto.class);
        productResponseDto.setProductType(productTypeDto);

        // Set product image list
        List<ProductImageDto> productImages = productImageRepository.findAllByProductId(productEntity.getId()).stream().map(item -> modelMapper.map(item, ProductImageDto.class)).toList();
        productResponseDto.setProductImages(productImages);

        return productResponseDto;
    }

    @Override
    public ProductResponseDto entityToDtoForList(ProductEntity productEntity) {
        ProductResponseDto productResponseDto = new ProductResponseDto();

        // Set main property values to show list
        productResponseDto.setId(productEntity.getId());
        productResponseDto.setProductCode(productEntity.getProductCode());
        productResponseDto.setProductName(productEntity.getProductName());
        productResponseDto.setUnit(productEntity.getUnit());
        productResponseDto.setMainImage(productEntity.getMainImage());
        productResponseDto.setCostPrice(productEntity.getCostPrice());
        productResponseDto.setRetailPrice(productEntity.getRetailPrice());
        productResponseDto.setSalePrice(productEntity.getSalePrice());
        productResponseDto.setInputQuantity(productEntity.getInputQuantity());
        productResponseDto.setSoldQuantity(productEntity.getSoldQuantity());
        productResponseDto.setRemainingQuantity(productEntity.getRemainingQuantity());

        return productResponseDto;
    }

}
