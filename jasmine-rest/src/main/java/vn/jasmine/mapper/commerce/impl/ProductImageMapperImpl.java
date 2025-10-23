package vn.jasmine.mapper.commerce.impl;

import vn.jasmine.dto.both.commerce.ProductImageDto;
import vn.jasmine.entity.commerce.ProductImageEntity;
import vn.jasmine.mapper.commerce.IProductImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductImageMapperImpl implements IProductImageMapper {

    @Override
    public ProductImageDto entityToDto(ProductImageEntity productImageEntity) {
        if (productImageEntity == null) {
            throw new IllegalArgumentException("Product Image Entity is null");
        }

        return ProductImageDto.builder().imageId(productImageEntity.getId()).imageURL(productImageEntity.getImageURL()).build();
    }

}
