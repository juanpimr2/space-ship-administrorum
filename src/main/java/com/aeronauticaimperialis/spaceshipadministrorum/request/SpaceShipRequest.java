package com.aeronauticaimperialis.spaceshipadministrorum.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpaceShipRequest {
    @NotBlank
    private String name; // Nombre de la nave

    @NotNull
    private String faction; // Facción de la nave
    
    @NotBlank
    private String description; // Descripción de la nave
}
