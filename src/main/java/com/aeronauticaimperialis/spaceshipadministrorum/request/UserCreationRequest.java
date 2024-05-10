package com.aeronauticaimperialis.spaceshipadministrorum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationRequest {
  @NotBlank
  @Schema(description = "Nombre de usuario", example = "Luke")
  private String username;
  @NotBlank
  @Schema(description = "Contraseña", example = "El_caminacielos123")
  private String password;

}
