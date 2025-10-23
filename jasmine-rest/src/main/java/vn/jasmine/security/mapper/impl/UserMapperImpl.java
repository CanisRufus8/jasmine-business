package vn.jasmine.security.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.jasmine.constants.JasmineCommonConst;
import vn.jasmine.security.dto.request.ResetPasswordRequestDto;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.dto.response.UserResponseDto;
import vn.jasmine.security.entity.RoleEntity;
import vn.jasmine.security.entity.UserEntity;
import vn.jasmine.security.mapper.IUserMapper;

import java.util.Set;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Component
public class UserMapperImpl implements IUserMapper {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto toUserResponseDto(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setScope(buildScope(user.getRoles()));

        return userResponseDto;

    }

    @Override
    public UserEntity mapSignUpRequestToUser(SignUpRequestDto signUpRequest) {
        UserEntity user = modelMapper.map(signUpRequest, UserEntity.class);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//        user.setProvider(OAuth2Provider.LOCAL);
        return user;
    }

    @Override
    public UserEntity mapResetPasswordRequestToUser(ResetPasswordRequestDto resetPasswordRequest) {
        UserEntity user = modelMapper.map(resetPasswordRequest, UserEntity.class);
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        return user;
    }

    private String buildScope(Set<RoleEntity> roles) {
        StringJoiner stringJoiner = new StringJoiner(JasmineCommonConst.SPACE);
        if (!CollectionUtils.isEmpty(roles)) {
            roles
                    .forEach(item -> stringJoiner.add(item.getName()));
        }
        return stringJoiner.toString();
    }

}
