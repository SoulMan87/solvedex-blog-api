package com.soulrebel.blog.commons;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.JOptionPane;
import java.util.Optional;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();

        final var username = JOptionPane.showInputDialog ("Enter 'admin' or 'user' as the username:");
        final var password = JOptionPane.showInputDialog ("Enter the password for " + username + ":");

        final var encodedPassword = Optional.ofNullable (username)
                .filter (u -> "admin".equals (u) || "user".equals (u))
                .map (u -> passwordEncoder.encode (password));
        if (encodedPassword.isPresent ()) {
            encodedPassword.ifPresent (p -> {
                JOptionPane.showMessageDialog (null, "Encoded Password for " + username + ": " + p);
                System.out.println ("Encoded Password for " + username + ": " + p);
            });
        } else {
            JOptionPane.showMessageDialog (null, "Invalid username. Please enter 'admin' or 'user'.");
        }
    }
}
