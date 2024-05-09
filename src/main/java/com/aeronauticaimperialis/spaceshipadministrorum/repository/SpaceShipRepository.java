package com.aeronauticaimperialis.spaceshipadministrorum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;

public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {

  List<SpaceShip> findByNameContainingIgnoreCase(String name);
}
