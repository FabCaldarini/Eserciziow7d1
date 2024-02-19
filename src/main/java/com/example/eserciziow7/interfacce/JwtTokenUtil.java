package com.example.eserciziow7.interfacce;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenUtil {
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token);
}

