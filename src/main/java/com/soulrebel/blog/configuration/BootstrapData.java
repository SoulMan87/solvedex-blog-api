package com.soulrebel.blog.configuration;

import com.soulrebel.blog.entity.Role;
import com.soulrebel.blog.entity.User;
import com.soulrebel.blog.repository.RoleRepository;
import com.soulrebel.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapData {

    final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void bootstrap() {
        if (roleRepository.count () != 0) {
            return;
        }

        List<Role> roles = List.of (
                createRole ("ROLE_USER"),
                createRole ("ROLE_ADMIN")
        );

        List<User> users = List.of (
                createUser ("user", "user", "user@user.com", Set.of (roles.get (0))),
                createUser ("admin", "admin", "admin@admin.com", Set.of (roles.get (0), roles.get (1)))
        );

        roleRepository.saveAll (roles);
        userRepository.saveAll (users);
    }

    private Role createRole(String name) {
        return Role.builder ()
                .name (name)
                .build ();
    }

    private User createUser(String name, String username, String email, Set<Role> roles) {
        return User.builder ()
                .username (username)
                .name (name)
                .password (passwordEncoder.encode (username))
                .email (email)
                .roles (roles)
                .build ();
    }
}
