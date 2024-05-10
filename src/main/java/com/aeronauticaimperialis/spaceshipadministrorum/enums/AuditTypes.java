package com.aeronauticaimperialis.spaceshipadministrorum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditTypes {
    CREATE_USER( "ALTA","Creación de Usuario"),
    CREATE_SPACE_SHIP("ALTA", "Creación de Nave Espacial"),
    MODIFY_SPACE_SHIP("MOD", "Modificación de Nave Espacial"),
    DELETE_SPACE_SHIP("BAJA", "Borrado de Nave Espacial"),
    GET_SPACE_SHIP("CON", "Consultado de Nave Espacial");

    private final String type;
    private final String event;

}
