package com.soulrebel.blog.commons;

import com.soulrebel.blog.security.JwtTokenProvider;
import com.soulrebel.blog.entity.User;
import com.soulrebel.blog.exception.BlogAPIException;
import com.soulrebel.blog.repository.RoleRepository;
import com.soulrebel.blog.repository.UserRepository;
import com.soulrebel.blog.rest.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class AuthenticationServiceCommons {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String IS_ALREADY_TAKEN = " is already taken";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";

    protected final AuthenticationManager authenticationManager;
    protected final UserRepository userRepository;
    protected final RoleRepository roleRepository;
    protected final PasswordEncoder passwordEncoder;
    protected final JwtTokenProvider jwtTokenProvider;

    protected User userBuilder(RegisterDto dto) {
        final var role = roleRepository.findByName (ROLE_USER)
                .orElseThrow (() -> new BlogAPIException (HttpStatus.BAD_REQUEST, ROLE_NOT_FOUND));
        return User.builder ()
                .name (dto.getName ())
                .username (dto.getUsername ())
                .email (dto.getEmail ())
                .password (passwordEncoder.encode (dto.getPassword ()))
                .roles (Set.of (role))
                .build ();
    }

}
