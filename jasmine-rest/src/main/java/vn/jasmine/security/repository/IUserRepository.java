package vn.jasmine.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.jasmine.security.entity.UserEntity;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
