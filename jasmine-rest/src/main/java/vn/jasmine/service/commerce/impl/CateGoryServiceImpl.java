package vn.jasmine.service.commerce.impl;

import vn.jasmine.dto.both.commerce.CategoryDto;
import vn.jasmine.repository.commerce.ICategoryRepository;
import vn.jasmine.service.commerce.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CateGoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(item -> modelMapper.map(item, CategoryDto.class)).toList();
    }

}
