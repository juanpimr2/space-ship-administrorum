package com.aeronauticaimperialis.spaceshipadministrorum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Autowired
  private UserDetailService userDetailService; // Inyección del servicio

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(registry -> {
                  registry.requestMatchers("/home", "/register/user","/v3/api-docs", "/swagger-ui/**", "/v3/api-docs/swagger-config", "/swagger-ui.html").permitAll();
                  registry.requestMatchers("/register/admin").hasRole("ADMIN");
                  registry.requestMatchers("/praiseTheEmperor").hasRole("ADMIN");
                  registry.anyRequest().authenticated();
              })
              .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
              .build();
  }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService); // Usar el servicio inyectado
        provider.setPasswordEncoder(passwordEncoder());
        return provider; 
    }

    @Bean
    @Lazy
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
