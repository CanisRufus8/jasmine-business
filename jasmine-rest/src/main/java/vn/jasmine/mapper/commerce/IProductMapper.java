package vn.jasmine.mapper.commerce;

import vn.jasmine.dto.response.commerce.ProductResponseDto;
import vn.jasmine.entity.commerce.ProductEntity;

public interface IProductMapper {

    ProductResponseDto entityToFullDataDto(ProductEntity productEntity);

    ProductResponseDto entityToDtoForList(ProductEntity productEntity);

}
