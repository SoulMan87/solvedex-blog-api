package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.LoginDto;
import com.soulrebel.blog.rest.RegisterDto;

public interface AuthenticationService {
    String login(final LoginDto loginDto);

    String register(final RegisterDto registerDto);
}
