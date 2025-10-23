package vn.jasmine.security.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import vn.jasmine.security.dto.request.SignUpRequestDto;
import vn.jasmine.security.entity.RoleEntity;
import vn.jasmine.security.mapper.IUserMapper;
import vn.jasmine.security.repository.IRoleRepository;
import vn.jasmine.security.repository.IUserRepository;
import vn.jasmine.security.service.IUserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IUserService userService;
    private final IRoleRepository roleRepository;
    private final IUserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            List<String> defaultRoles = Arrays.asList(
                    "SUPER_ADMIN",
                    "ADMIN",
                    "MANAGER",
                    "LANDLORD",
                    "TENANT",
                    "LEADER",
                    "EMPLOYEE",
                    "USER"
            );

            defaultRoles.forEach(roleName -> {
                RoleEntity role = new RoleEntity();
                role.setName(roleName);
                role.setDescription(roleName + " role");
                roleRepository.save(role);
            });

            System.out.println("✅ Default roles have been inserted into database.");
        } else {
            System.out.println("ℹ️ Roles already exist, skip seeding.");
        }


        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().username("admin").password("Thienduy1368").email("thienduy1368@gmail.com").roles(Set.of("ADMIN")).build();

        if (!userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            userService.signUpUser(signUpRequestDto);
        }

    }

}