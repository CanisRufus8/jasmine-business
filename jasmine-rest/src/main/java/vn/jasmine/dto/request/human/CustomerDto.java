package vn.jasmine.dto.request.human;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerDto {

    private UUID id;
    private String username;
    private String customerName;

}
