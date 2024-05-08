package com.aeronauticaimperialis.spaceshipadministrorum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditTypes {
    CREATE_USER("Creación Usuario", "CU"),
    CREATE_SHIP("Creación Nave", "CN"),
    MOD_NAVE("Modificación Nave", "MN"),
    DEL_NAVE("Borrado de Nave", "BN");

    private final String description;
    private final String type;
}
