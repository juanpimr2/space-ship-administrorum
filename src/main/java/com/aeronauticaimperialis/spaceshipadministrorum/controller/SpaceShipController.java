package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.SpaceShipResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/spaceships")
@Slf4j
public class SpaceShipController {

  private SpaceShipService spaceShipService;

  public SpaceShipController(SpaceShipService spaceShipService) {
    this.spaceShipService = spaceShipService;
  }

  @Operation(summary = "Obtener todas las naves espaciales",
      description = "Obtiene todas las naves espaciales almacenadas en la base de datos.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa")})
  @GetMapping(value = "/all")
  public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShips(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize) {
    log.info("Entrando en controller getAllSpaceShips");
    return spaceShipService.getAllSpaceShip(pageNumber, pageSize);
  }

  @Operation(summary = "Crear una nueva nave espacial",
      description = "Crea una nueva nave espacial con los detalles proporcionados en la solicitud.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<SpaceShipResponse> createSpaceShip(
      @RequestBody SpaceShipRequest spaceShip) {
    return spaceShipService.createSpaceShipTask(spaceShip);
  }

  @Operation(summary = "Obtener una nave espacial por su ID",
      description = "Obtiene una nave espacial por su ID.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa"),
      @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
  @GetMapping(value = "/{id}")
  public ResponseEntity<SpaceShipResponse> getSpaceShipById(@PathVariable Long id) {
    return spaceShipService.getSpaceShipById(id);
  }

  @Operation(summary = "Consultar naves por nombre",
      description = "Obtiene todas las naves cuyo nombre contiene el valor proporcionado.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa"),
      @ApiResponse(responseCode = "400", description = "Parámetro inválido")})
  @GetMapping(value = "/search")
  public ResponseEntity<List<SpaceShipResponse>> searchSpaceShipsByName(@RequestParam String name) {
    return spaceShipService.searchSpaceShipsByName(name);
  }

  @Operation(summary = "Modificar una nave espacial",
      description = "Modifica los detalles de una nave espacial existente.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa"),
      @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<SpaceShipResponse> updateSpaceShip(@PathVariable Long id,
      @RequestBody SpaceShipRequest spaceShip) {
    return spaceShipService.updateSpaceShip(id, spaceShip);
  }

  @Operation(summary = "Eliminar una nave espacial",
      description = "Elimina una nave espacial existente por su ID.")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "204", description = "Nave espacial eliminada"),
          @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteSpaceShip(@PathVariable Long id) {
    return spaceShipService.deleteSpaceShip(id);
  }


  @Operation(summary = "Borrado de cache")
  @GetMapping(path = "/evict")
  public ResponseEntity<String> evictCache() {
    spaceShipService.clearCache();
    return ResponseEntity.ok("Se ha limpiando el cache exitosamente");
  }
}
