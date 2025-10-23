package vn.jasmine.security.service;

import vn.jasmine.security.entity.RefreshTokenEntity;
import vn.jasmine.security.exception.instance.TokenRefreshException;
import vn.jasmine.security.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Value("${app.jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public RefreshTokenEntity createRefreshToken(String jwt, String username) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().accessToken(jwt).username(username).expiryDate(Instant.now().plusMillis(refreshTokenDurationMs)).build();

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public Optional<RefreshTokenEntity> findByRefreshToken(UUID token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    @Override
    public void deleteRefreshToken(String jwt) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByAccessToken(jwt).orElseThrow(EntityNotFoundException::new);
        refreshTokenRepository.delete(refreshTokenEntity);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken().toString(), "Refresh token was expired. Please make a new sign in request");
        }

        return token;
    }

}
