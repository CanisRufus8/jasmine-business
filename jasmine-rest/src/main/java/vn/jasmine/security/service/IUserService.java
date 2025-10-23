package vn.jasmine.security.service;

import vn.jasmine.security.dto.request.ResetPasswordRequestDto;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.dto.response.SignUpResponseDto;
import vn.jasmine.security.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserEntity> getUsers();

    UserEntity getUserByUsername(String username);

    Optional<UserEntity> getUserByEmail(String email);

    boolean existUserWithUsername(String username);

    boolean existUserWithEmail(String email);

    UserEntity validateAndGetUserByUsername(String username);

    SignUpResponseDto signUpUser(SignUpRequestDto signUpRequest);

    void deleteUser(UserEntity user);

    String getResetPasswordToken(String email);

    String resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
}
