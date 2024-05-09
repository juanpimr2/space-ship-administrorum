package com.aeronauticaimperialis.spaceshipadministrorum.response;

import lombok.Data;

@Data
public class SpaceShipResponse {
  private Long id;
  private String name; // Nombre de la nave
  private String faction; // Facción de la nave
  private String description; // Descripción de la nave

}
