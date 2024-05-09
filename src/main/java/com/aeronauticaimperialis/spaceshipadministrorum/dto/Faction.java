package com.aeronauticaimperialis.spaceshipadministrorum.dto;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faction")
@Data
@NoArgsConstructor
public class Faction {

    @Id
    @NotBlank
    private String code; // Código de la facción (clave primaria y único)
    private String name; // Nombre de la facción
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private Boolean active;
}
