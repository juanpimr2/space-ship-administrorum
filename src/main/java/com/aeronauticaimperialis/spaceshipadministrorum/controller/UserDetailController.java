package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.EmptyUserNameOnRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.UserAlreadyExistsException;
import com.aeronauticaimperialis.spaceshipadministrorum.request.UserCreationRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.UserCreationResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Controlador para la gestion de usuarios",
    description = "Gestion de todos los miembros del Imperio de la humanidad")
@RestController
@RequestMapping("/register")
public class UserDetailController {

  private final UserDetailService userDetailService;

  public UserDetailController(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @Operation(summary = "Crear usuario con rol de usuario",
      description = "Creación de usuario a la orden del emperador")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa")})
  @PostMapping(value = "/user", produces = "application/json")
  public ResponseEntity<UserCreationResponse> createUser(
      @RequestBody UserCreationRequest userRequest)
      throws EmptyUserNameOnRequest, UserAlreadyExistsException {
    return userDetailService.createUser(userRequest);
  }

  @Operation(summary = "Crear usuario con rol de administrador",
      description = "Creación de un administratorum regentus")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa")})
  @PostMapping("/admin")
  public ResponseEntity<UserCreationResponse> createAdmin(
      @RequestBody UserCreationRequest userRequest) throws EmptyUserNameOnRequest {
    return userDetailService.createAdmin(userRequest);
  }

}
