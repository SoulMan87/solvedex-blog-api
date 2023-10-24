package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.LoginDto;
import com.soulrebel.blog.rest.RegisterDto;
import com.soulrebel.blog.rest.model.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(name = "Authentication",
        description = "REST API designed to facilitate the management and interaction with Authentication resources")
@RequestMapping("/auth")
public interface IAuthenticationController {

    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "User authenticated")
    @PostMapping(value = {"/login", "/signin"})
    ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDto loginDto);

    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "User authenticated")
    @PostMapping(value = {"/register", "/signup"})
    ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto);
}
