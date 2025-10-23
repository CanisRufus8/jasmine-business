package vn.jasmine.security.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    String accessToken;
    String refreshToken;

}