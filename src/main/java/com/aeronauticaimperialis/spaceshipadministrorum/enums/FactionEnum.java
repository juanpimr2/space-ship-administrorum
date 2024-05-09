package com.aeronauticaimperialis.spaceshipadministrorum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FactionEnum {
  IMPERIUM("IP","IMPERIUM"),
  CHAOS("CH","CHAOS"),
  ELDAR("EL", "ELDAR"),
  ORKS("ORKS", "ORKS"),
  NECRONS("NE", "NECRONS");
    
  
    private final String code;
    private final String description;
}
