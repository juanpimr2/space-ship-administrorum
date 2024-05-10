package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.FactionNotFoundException;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.FactionRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.response.FactionResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FactionService {
  
  private final FactionRepository factionRepository;
  
  public FactionService(FactionRepository factionRepository) {
    this.factionRepository = factionRepository;
  }

  /**
   * Obtiene una faccion por su codigo
   * @param factionCode
   * @return Entidad Faction
   */
  public Faction getFactionByCode(String factionCode) {
    Optional<Faction> factionOptional = factionRepository.findByCode(factionCode);
    return factionOptional.orElseThrow(() -> new FactionNotFoundException("Facción no encontrada con el código: " + factionCode));
  }

  public ResponseEntity<List<FactionResponse>> getAllFactions(int pageNumber, int pageSize) {
    try {
      log.info("Iniciando getAllSpaceShip");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Faction> factionPage = factionRepository.findAll(pageable);
        List<FactionResponse> factionResponse = factionPage.getContent().stream()
                .map(faction -> {
                    log.info("Mapeando: {}", faction);
                    FactionResponse response = new FactionResponse();
                    BeanUtils.copyProperties(faction, response);
                    return response;
                })
                .toList();
        // Devolver ResponseEntity con estado de éxito y lista de naves espaciales paginadas
        log.info("Finalizando getAllSpaceShip");
        return ResponseEntity.ok(factionResponse);
    } catch (Exception e) {
      log.info("Error: {}", e);
      log.info("Finalizando getAllSpaceShip");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @CacheEvict(cacheNames = { "getAllFactions" }, allEntries = true)
  public void clearCache() {
      log.info("Clear Cache...");
  }
}
