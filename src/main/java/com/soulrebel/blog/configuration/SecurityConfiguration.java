package com.soulrebel.blog.configuration;

import com.soulrebel.blog.security.JwtAuthenticationEntryPoint;
import com.soulrebel.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@SecurityScheme(name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", scheme = "bearer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager ();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf ().disable ();
        http.cors ().disable ();
        http.authorizeHttpRequests().requestMatchers(antMatcher("/h2-console/**")).permitAll()
                .and().csrf().ignoringRequestMatchers(antMatcher("/h2-console/**"))
                .and().headers().frameOptions().disable();

        http.authorizeHttpRequests ().requestMatchers (HttpMethod.GET).permitAll ();
        http.authorizeHttpRequests ().requestMatchers ("/api/v1/auth/**").permitAll ();
        http.authorizeHttpRequests ().anyRequest ().authenticated ();

        http.exceptionHandling (handling -> handling.authenticationEntryPoint (jwtAuthenticationEntryPoint));
        http.sessionManagement (management -> management.sessionCreationPolicy (SessionCreationPolicy.STATELESS));

        http.addFilterBefore (jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build ();
    }
}
