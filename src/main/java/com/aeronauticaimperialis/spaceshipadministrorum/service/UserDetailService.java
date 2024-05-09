package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.request.UserCreationRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.UserCreationResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.utils.Encoder;
import com.aeronauticaimperialis.spaceshipadministrorum.utils.UtilsImperatoris;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

  private UserRepository userRepository;
  private UtilsImperatoris utilsImperatoris;
  private Encoder encoder;


  @Autowired
  public UserDetailService(UserRepository userRepository, Encoder encoder,
      UtilsImperatoris utilsImperatoris) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.utilsImperatoris = utilsImperatoris;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserDetail> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      var userObj = optionalUser.get();

      return User.builder().username(userObj.getUsername()).password(userObj.getPassword())
          .roles(utilsImperatoris.getRoles(userObj)).build();

    } else {
      throw new UsernameNotFoundException(username);
    }
  }


  public ResponseEntity<UserCreationResponse> createUser(UserCreationRequest userRequest) {
    UserCreationResponse response = new UserCreationResponse();
    if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // Verificar si el nombre de usuario ya existe en la base de datos
    if (usernameExists(userRequest.getUsername())) {
      response.setDescription("Username already exists.");
      response.setStatus(HttpStatus.BAD_REQUEST.toString());
      return ResponseEntity.badRequest().body(response);
    }

    // Crear el objeto UserDetail
    UserDetail userDetail = new UserDetail();
    userDetail.setUsername(userRequest.getUsername());
    userDetail.setPassword(encoder.encodePassword(userRequest.getPassword()));
    userDetail.setRole(Constants.USER_ROLE);
    userDetail.setCreatedAt(LocalDateTime.now());
    userDetail.setUpdatedAt(null);
    userDetail.setActive(true);

    try {
      userRepository.save(userDetail);
      response.setDescription(String.format(Constants.CREATION_MESSAGE, userRequest.getUsername()));
      response.setMissive(String.format(Constants.WELCOME_MESSAGE, userRequest.getUsername()));
      response.setStatus(HttpStatus.OK.toString());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Método para verificar si un nombre de usuario ya existe en la base de datos
  private boolean usernameExists(String username) {
    Optional<UserDetail> existingUser = userRepository.findByUsername(username);
    return existingUser.isPresent();
  }

  public ResponseEntity<UserCreationResponse> createAdmin(UserCreationRequest userRequest) {
    UserCreationResponse response = new UserCreationResponse();
    if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    UserDetail userDetail = new UserDetail();

    userDetail.setUsername(userRequest.getUsername());
    userDetail.setPassword(encoder.encodePassword(userRequest.getPassword()));
    userDetail.setRole(Constants.ADMIN_ROLE);
    userDetail.setCreatedAt(LocalDateTime.now());
    userDetail.setUpdatedAt(null);
    userDetail.setActive(true);
    try {
      userRepository.save(userDetail);
      response.setDescription(String.format(Constants.CREATION_MESSAGE, userRequest.getUsername()));
      response
          .setMissive(String.format(Constants.WELCOME_ADMIN_MESSAGE, userRequest.getUsername()));
      response.setStatus(HttpStatus.OK.toString());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
