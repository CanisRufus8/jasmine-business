package vn.jasmine.security.repository;

import vn.jasmine.security.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByRefreshToken(UUID refreshToken);

    Optional<RefreshTokenEntity> findByAccessToken(String accessToken);

}