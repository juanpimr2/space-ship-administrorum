package com.aeronauticaimperialis.spaceshipadministrorum.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Encoder {
  
  private final PasswordEncoder passwordEncoder;

  public Encoder(@Lazy PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  // Método para codificar la contraseña
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}
