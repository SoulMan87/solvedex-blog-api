package com.soulrebel.blog.service.impl;

import com.soulrebel.blog.commons.AuthenticationServiceCommons;
import com.soulrebel.blog.security.JwtTokenProvider;
import com.soulrebel.blog.exception.BlogAPIException;
import com.soulrebel.blog.repository.RoleRepository;
import com.soulrebel.blog.repository.UserRepository;
import com.soulrebel.blog.rest.LoginDto;
import com.soulrebel.blog.rest.RegisterDto;
import com.soulrebel.blog.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AuthenticationServiceImpl extends AuthenticationServiceCommons implements AuthenticationService {
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserRepository userRepository, RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        super (authenticationManager, userRepository, roleRepository, passwordEncoder, jwtTokenProvider);
    }

    @Override
    public String login(final LoginDto loginDto) {
        return Optional.of (authenticationManager.authenticate (new UsernamePasswordAuthenticationToken (
                        loginDto.getUsernameOrEmail (),
                        loginDto.getPassword ()
                )))
                .map (authentication -> {
                    SecurityContextHolder.getContext ().setAuthentication (authentication);
                    return jwtTokenProvider.generateJwtToken (authentication);
                })
                .orElseThrow (() -> new RuntimeException (INVALID_CREDENTIALS));
    }

    @Override
    public String register(final RegisterDto registerDto) {
        Stream.of (registerDto.getUsername (), registerDto.getEmail (), registerDto.getName ())
                .filter (field -> userRepository.existsByUsername (field) || userRepository.existsByEmail (field)
                        || userRepository.existsByName (field)).findAny ().ifPresent (field -> {
                    throw new BlogAPIException (HttpStatus.BAD_REQUEST, field + IS_ALREADY_TAKEN);
                });
        userRepository.save (userBuilder (registerDto));
        return USER_REGISTERED_SUCCESSFULLY;
    }
}
