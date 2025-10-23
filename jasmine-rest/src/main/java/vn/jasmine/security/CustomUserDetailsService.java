package vn.jasmine.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.jasmine.security.entity.UserEntity;
import vn.jasmine.security.repository.IRoleRepository;
import vn.jasmine.security.repository.IUserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity appUser = userRepository.findByUsername(username).orElseThrow();
        Set<SimpleGrantedAuthority> authorities = appUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        return mapUserToCustomUserDetails(appUser, authorities);
    }

    private CustomUserDetails mapUserToCustomUserDetails(UserEntity user, Set<SimpleGrantedAuthority> authorities) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUserId(user.getId());
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setAuthorities(authorities);

        return customUserDetails;
    }

}