package com.aeronauticaimperialis.spaceshipadministrorum.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpaceShipRequest {
    @NotBlank
    private String name; // Nombre de la nave

    @NotNull
    @Schema(description = "Facción de la nave", required = true, allowableValues = {"IP", "CH", "EL", "ORKS", "NE"}, example = "IP")
    private String faction; // Facción de la nave
    
    @NotBlank
    private String description; // Descripción de la nave
}
