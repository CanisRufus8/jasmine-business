package vn.jasmine.dto.both.commerce;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductImageDto {

    private UUID imageId;
    private String imageURL;

}
