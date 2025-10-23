package vn.jasmine.repository.human;

import vn.jasmine.entity.human.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
