package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.FactionNotFoundException;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.FactionRepository;

@Component
public class FactionService {
  
  private final FactionRepository factionRepository;
  
  public FactionService(FactionRepository factionRepository) {
    this.factionRepository = factionRepository;
  }

  public Faction getFactionByCode(String factionCode) {
    Optional<Faction> factionOptional = factionRepository.findByCode(factionCode);
    return factionOptional.orElseThrow(() -> new FactionNotFoundException("Facción no encontrada con el código: " + factionCode));
  }
}
