package vn.jasmine.dto.both.commerce;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryDto {

    private UUID id;
    private String categoryCode;
    private String categoryName;
    private String description;

}
