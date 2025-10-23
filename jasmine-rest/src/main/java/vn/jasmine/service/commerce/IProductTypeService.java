package vn.jasmine.service.commerce;

import vn.jasmine.dto.both.commerce.ProductTypeDto;

import java.util.List;

public interface IProductTypeService {

    List<ProductTypeDto> findAll();
}
