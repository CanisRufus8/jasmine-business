package vn.jasmine.security.service;

import vn.jasmine.security.entity.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface IRefreshTokenService {

    RefreshTokenEntity createRefreshToken(String jwt, String username);

    Optional<RefreshTokenEntity> findByRefreshToken(UUID token);

    void deleteRefreshToken(String jwt);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshTokenEntity);
}
