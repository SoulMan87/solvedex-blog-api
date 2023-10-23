package com.soulrebel.blog.security;


import com.soulrebel.blog.entity.User;
import com.soulrebel.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_WITH_USER_NAME_OR_EMAIL = "User not found with username or email:";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        final var user = userRepository.findByUsernameOrEmail (usernameOrEmail, usernameOrEmail)
                .orElseThrow (() ->
                        new UsernameNotFoundException (USER_NOT_FOUND_WITH_USER_NAME_OR_EMAIL + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User (user.getEmail (),
                user.getPassword (), user.getRoles ().stream ()
                .map (role -> new SimpleGrantedAuthority (role.getName ()))
                .toList ()
        );
    }

}
