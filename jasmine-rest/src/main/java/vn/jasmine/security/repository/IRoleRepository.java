package vn.jasmine.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.jasmine.security.entity.RoleEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {

    List<RoleEntity> findByNameIn(Collection<String> names);

}
