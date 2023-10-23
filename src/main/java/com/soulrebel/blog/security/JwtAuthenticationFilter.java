package com.soulrebel.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain
    ) throws ServletException, IOException {
        final var jwtToken = getTokenFromRequestHeaders (request);

        Optional.ofNullable (jwtToken)
                .filter (jwtTokenProvider::validateJwtToken)
                .ifPresent (token -> {
                    final var username = jwtTokenProvider.getUsernameFromJwtToken (token);
                    final var userDetails = userDetailsService.loadUserByUsername (username);
                    final var authenticationToken = new UsernamePasswordAuthenticationToken (
                            userDetails, null, userDetails.getAuthorities ()
                    );
                    authenticationToken.setDetails (new WebAuthenticationDetailsSource ().buildDetails (request));
                    SecurityContextHolder.getContext ().setAuthentication (authenticationToken);

                });
        filterChain.doFilter (request, response);
    }

    private String getTokenFromRequestHeaders(final HttpServletRequest request) {
        final var bearerToken = request.getHeader (AUTHORIZATION);
        return Optional.ofNullable (bearerToken)
                .filter (token -> token.startsWith (BEARER))
                .map (token -> token.substring (7))
                .orElse (null);
    }
}
