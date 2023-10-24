package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.LoginDto;
import com.soulrebel.blog.rest.RegisterDto;
import com.soulrebel.blog.rest.model.JwtAuthenticationResponse;
import com.soulrebel.blog.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController implements IAuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(final LoginDto loginDto) {
        final var accessToken = authenticationService.login (loginDto);
        return ResponseEntity.ok (new JwtAuthenticationResponse (accessToken));
    }

    @Override
    public ResponseEntity<String> registerUser(final RegisterDto registerDto) {
        return ResponseEntity.ok (authenticationService.register (registerDto));
    }
}
