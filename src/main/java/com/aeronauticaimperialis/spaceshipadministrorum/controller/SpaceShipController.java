package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

@Validated
@RestController
@RequestMapping("/spaceships")
public class SpaceShipController {
    
    @Autowired
    private SpaceShipService spaceShipService;


    @Operation(summary = "Obtener todas las naves espaciales", description = "Obtiene todas las naves espaciales almacenadas en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShips(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize)  {
      return spaceShipService.getAllSpaceShip(pageNumber, pageSize);
    }

    @Operation(summary = "Crear una nueva nave espacial", description = "Crea una nueva nave espacial con los detalles proporcionados en la solicitud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<SpaceShipResponse> createSpaceShip(@RequestBody SpaceShipRequest spaceShip) {
        return spaceShipService.createSpaceShipTask(spaceShip);
    }
}
