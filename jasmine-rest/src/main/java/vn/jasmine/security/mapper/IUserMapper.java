package vn.jasmine.security.mapper;

import vn.jasmine.security.dto.request.ResetPasswordRequestDto;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.dto.response.UserResponseDto;
import vn.jasmine.security.entity.UserEntity;

public interface IUserMapper {

    UserResponseDto toUserResponseDto(UserEntity user);

    UserEntity mapSignUpRequestToUser(SignUpRequestDto signUpRequest);

    UserEntity mapResetPasswordRequestToUser(ResetPasswordRequestDto resetPasswordRequest);

}
