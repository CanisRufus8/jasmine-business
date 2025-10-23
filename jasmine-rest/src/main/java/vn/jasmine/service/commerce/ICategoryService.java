package vn.jasmine.service.commerce;


import vn.jasmine.dto.both.commerce.CategoryDto;

import java.util.List;

public interface ICategoryService {

    List<CategoryDto> findAll();
}
