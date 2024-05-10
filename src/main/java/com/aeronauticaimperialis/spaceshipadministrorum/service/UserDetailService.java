package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.EmptyUserNameOnRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.UserAlreadyExistsException;
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

  public ResponseEntity<UserCreationResponse> createUser(UserCreationRequest userRequest)
      throws EmptyUserNameOnRequest, UserAlreadyExistsException {
    log.info("Creando usuario...");
    UserCreationResponse response = new UserCreationResponse();

    validateUserRequest(userRequest);

    if (usernameExists(userRequest.getUsername())) {
      throw new UserAlreadyExistsException("El nombre de usuario ya está en uso.");
    }

    UserDetail userDetail = createUserDetail(userRequest, Constants.USER_ROLE);

    try {
      userRepository.save(userDetail);
      response.setDescription(String.format(Constants.CREATION_MESSAGE, userRequest.getUsername()));
      response.setMissive(String.format(Constants.WELCOME_MESSAGE, userRequest.getUsername()));
      response.setStatus(HttpStatus.OK.toString());
      log.info("Usuario creado exitosamente: {}", userRequest.getUsername());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error creando usuario: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public ResponseEntity<UserCreationResponse> createAdmin(UserCreationRequest userRequest)
      throws EmptyUserNameOnRequest {
    log.info("Creando sumo administrador poderosisimo...");
    UserCreationResponse response = new UserCreationResponse();

    validateUserRequest(userRequest);

    UserDetail userDetail = createUserDetail(userRequest, Constants.ADMIN_ROLE);

    try {
      userRepository.save(userDetail);
      response.setDescription(String.format(Constants.CREATION_MESSAGE, userRequest.getUsername()));
      response
          .setMissive(String.format(Constants.WELCOME_ADMIN_MESSAGE, userRequest.getUsername()));
      response.setStatus(HttpStatus.OK.toString());
      log.info("Administrador creado exitosamente: {}", userRequest.getUsername());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("Error creando Administrador: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  private void validateUserRequest(UserCreationRequest userRequest) throws EmptyUserNameOnRequest {
    if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
      throw new EmptyUserNameOnRequest("El nombre de usuario es obligatorio.");
    }
  }

  private UserDetail createUserDetail(UserCreationRequest userRequest, String role) {
    UserDetail userDetail = new UserDetail();
    userDetail.setUsername(userRequest.getUsername());
    userDetail.setPassword(encoder.encodePassword(userRequest.getPassword()));
    userDetail.setRole(role);
    userDetail.setCreatedAt(LocalDateTime.now());
    userDetail.setUpdatedAt(null);
    userDetail.setActive(true);
    return userDetail;
  }

  private boolean usernameExists(String username) {
    Optional<UserDetail> existingUser = userRepository.findByUsername(username);
    return existingUser.isPresent();
  }
}
