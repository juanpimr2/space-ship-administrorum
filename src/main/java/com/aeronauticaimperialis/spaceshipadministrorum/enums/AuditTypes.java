package com.aeronauticaimperialis.spaceshipadministrorum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditTypes {
    CREATE_USER("Creación Usuario", "CU"),
    CREATE_SPACE_SHIP("Creación de Nave Espacial", "CNE"),
    MOD_NAVE("Modificación Nave *HEREJIA*", "MNE"),
    DEL_NAVE("Borrado de Nave es un deber vengarla", "BNE");

    private final String description;
    private final String type;
}
