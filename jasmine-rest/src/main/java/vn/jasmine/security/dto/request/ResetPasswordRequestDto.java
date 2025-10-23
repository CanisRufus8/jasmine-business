package vn.jasmine.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequestDto {

    @Schema(example = "token")
    @NotBlank
    private String token;

    @Schema(example = "password")
    @NotBlank
    private String password;

}
