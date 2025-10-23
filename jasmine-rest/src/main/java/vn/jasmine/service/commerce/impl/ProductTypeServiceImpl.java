package vn.jasmine.service.commerce.impl;

import vn.jasmine.dto.both.commerce.ProductTypeDto;
import vn.jasmine.repository.commerce.IProductTypeRepository;
import vn.jasmine.service.commerce.IProductTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements IProductTypeService {

    private final IProductTypeRepository productTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductTypeDto> findAll() {
        return productTypeRepository.findAll().stream().map(item -> modelMapper.map(item, ProductTypeDto.class)).toList();
    }

}
