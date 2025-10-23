package vn.jasmine.security.repository;

import vn.jasmine.security.entity.BlackListJWTEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJWTRepository extends JpaRepository<BlackListJWTEntity, UUID> {

    boolean existsByToken(String token);

}
