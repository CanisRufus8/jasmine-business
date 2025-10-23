package vn.jasmine.security.service;

import vn.jasmine.security.entity.BlackListJWTEntity;
import vn.jasmine.security.repository.IJWTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JWTServiceImpl implements IJWTService {

    private final IJWTRepository jwtRepository;

    @Override
    public void saveBlackListToken(String jwt) {
        BlackListJWTEntity blackListJWTEntity = BlackListJWTEntity.builder().token(jwt).build();
        jwtRepository.save(blackListJWTEntity);
    }

    @Override
    public boolean isBlackListToken(String token) {
        return jwtRepository.existsByToken(token);
    }

}
