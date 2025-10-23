package vn.jasmine.security.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDto {

    String username;
    String email;
    String scope;

}