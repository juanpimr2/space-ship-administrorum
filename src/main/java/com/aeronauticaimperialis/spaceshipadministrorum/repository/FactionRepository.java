package com.aeronauticaimperialis.spaceshipadministrorum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import lombok.Data;

public interface FactionRepository extends JpaRepository<Faction, String> {
  
  
}
