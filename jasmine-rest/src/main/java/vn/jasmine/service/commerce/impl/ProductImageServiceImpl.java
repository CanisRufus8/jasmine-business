package vn.jasmine.service.commerce.impl;

import vn.jasmine.dto.both.commerce.ProductImageDto;
import vn.jasmine.entity.commerce.ProductEntity;
import vn.jasmine.entity.commerce.ProductImageEntity;
import vn.jasmine.repository.commerce.IProductImageRepository;
import vn.jasmine.service.commerce.IProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements IProductImageService {

    private final IProductImageRepository productImageRepository;

    @Override
    public void saveOrUpdate(ProductEntity product, List<ProductImageDto> productImages) {
        productImageRepository.clearImagesOfProduct(product.getId());
        productImages.forEach(item -> {
            log.info(item.toString());
            productImageRepository.save(new ProductImageEntity(null, item.getImageURL(), product.getId()));
        });
    }

}
