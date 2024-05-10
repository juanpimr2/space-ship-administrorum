package com.aeronauticaimperialis.spaceshipadministrorum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpaceShipRequest {
    @NotBlank
    @Schema(description = "Nombre de la nave", example = "X-WING")
    private String name;

    @NotNull
    @Schema(description = "Codigo de la faccion, normalmente representado por las iniciales de la misma", allowableValues = {"IP", "CH", "EL", "ORKS", "NE"}, example = "IP")
    private String faction; 
    
    @NotBlank
    @Schema(description = "Descripcion de nave ", example = "Nave insignia de combate de los pilotos dentro de las flotas Rebeldes... molan! tienen unidades R2's y sus alas forma de X!!")
    private String description; 
}
