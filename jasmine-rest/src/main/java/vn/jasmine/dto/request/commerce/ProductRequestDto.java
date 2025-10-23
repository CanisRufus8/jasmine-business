package vn.jasmine.dto.request.commerce;

import vn.jasmine.dto.both.commerce.ProductImageDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductRequestDto {

    private Long id;
    private String productCode;
    private String productName;
    private String unit;
    private String title;
    private String mainImage;
    private String information;
    private String description;
    private String notes;
    private Integer weight;
    private Double costPrice;
    private Double wholesalePrice;
    private Double retailPrice;
    private Integer discountPercent;
    private Double salePrice;
    private Integer inputQuantity;
    private Integer soldQuantity;
    private Integer remainingQuantity;
    private Float reviewScore;
    private UUID productTypeId;
    private UUID categoryId;
    List<ProductImageDto> productImages;
    private Byte activeFlg;

}
