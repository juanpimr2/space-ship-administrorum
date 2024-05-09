package com.aeronauticaimperialis.spaceshipadministrorum.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;

public interface FactionRepository extends JpaRepository<Faction, String> {

  Optional<Faction> findByCode(String faction);
  
  
}
