package vn.jasmine.service.commerce;

import vn.jasmine.dto.both.commerce.ProductImageDto;
import vn.jasmine.entity.commerce.ProductEntity;

import java.util.List;

public interface IProductImageService {
    void saveOrUpdate(ProductEntity product, List<ProductImageDto> productImages);

}
