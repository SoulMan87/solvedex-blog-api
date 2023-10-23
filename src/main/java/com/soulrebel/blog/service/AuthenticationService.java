package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.LoginDto;
import com.soulrebel.blog.rest.RegisterDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
