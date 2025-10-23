package vn.jasmine.mapper.commerce;

import vn.jasmine.dto.both.commerce.ProductImageDto;
import vn.jasmine.entity.commerce.ProductImageEntity;

public interface IProductImageMapper {

    ProductImageDto entityToDto(ProductImageEntity productImageEntity);

}
