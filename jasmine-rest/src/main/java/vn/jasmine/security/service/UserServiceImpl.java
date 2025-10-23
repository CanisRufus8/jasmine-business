package vn.jasmine.security.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.jasmine.constants.JasmineMessageConst;
import vn.jasmine.security.dto.request.ResetPasswordRequestDto;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.dto.response.SignUpResponseDto;
import vn.jasmine.security.entity.RoleEntity;
import vn.jasmine.security.entity.UserEntity;
import vn.jasmine.security.enums.AccountStatus;
import vn.jasmine.security.mapper.IUserMapper;
import vn.jasmine.security.repository.IRoleRepository;
import vn.jasmine.security.repository.IUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper;

    @Override
    public List<UserEntity> getUsers() {
        return List.of();
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(JasmineMessageConst.USER_NOT_FOUND, username)));

        return user;
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean existUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity validateAndGetUserByUsername(String username) {
        return null;
    }

    @Override
    @Transactional
    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequest) {

        // Save user
        UserEntity userEntity = UserEntity.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        // Get role from DB by input
        Set<RoleEntity> roleEntities = new HashSet<>(roleRepository.findByNameIn(signUpRequest.getRoles()));
        userEntity.setRoles(roleEntities);
        userRepository.save(userEntity);

        return SignUpResponseDto.builder().username(signUpRequest.getUsername()).build();
    }

    @Override
    public void deleteUser(UserEntity user) {
        // TODO document why this method is empty
    }

    @Override
    public String getResetPasswordToken(String email) {
        return "";
    }

    @Override
    public String resetPassword(ResetPasswordRequestDto resetPasswordRequest) {
        UserEntity user = userMapper.mapResetPasswordRequestToUser(resetPasswordRequest);
        return "";
    }

}
