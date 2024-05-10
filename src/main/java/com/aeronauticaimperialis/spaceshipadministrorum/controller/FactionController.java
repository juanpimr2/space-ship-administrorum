package com.aeronauticaimperialis.spaceshipadministrorum.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.response.FactionResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.FactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/factions")
public class FactionController {
  private FactionService factionService;

  public FactionController(FactionService factionService) {
    this.factionService = factionService;
  }

  @Operation(summary = "Obtener todas las facciones",
      description = "Obtiene todas las facciones del universo de warhammer 40k.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa")})
  @GetMapping(value = "/all")
  public ResponseEntity<List<FactionResponse>> getAllFactions(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize) {
    return factionService.getAllFactions(pageNumber, pageSize);
  }
  
  @Operation(summary = "Borrado de cache")
  @GetMapping(path = "/evict")
  public ResponseEntity<String> evictCache() {
    factionService.clearCache();
      return ResponseEntity.ok("Cache successfully clean");
  }
}
