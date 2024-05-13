package com.aeronauticaimperialis.spaceshipadministrorum.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.FactionNotFoundException;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.SpaceShipRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.SpaceShipResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.FactionService;
import com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService;

class SpaceShipServiceTest {

  private SpaceShipService spaceShipService;
  private SpaceShipRepository spaceShipRepositoryMock;
  private FactionService factionServiceMock;

  @BeforeEach
  public void setUp() {
    spaceShipRepositoryMock = mock(SpaceShipRepository.class);
    factionServiceMock = mock(FactionService.class);
    spaceShipService = new SpaceShipService(spaceShipRepositoryMock, factionServiceMock);
  }

  @Test
  void testCreateSpaceShipTask_Success() {
    SpaceShipRequest request = new SpaceShipRequest();
    request.setName("Test Ship");
    request.setDescription("Test Description");
    request.setFaction("Test Faction");

    Faction faction = new Faction();
    faction.setCode("Test Faction");
    faction.setDescription("Test Faction Description");

    when(factionServiceMock.getFactionByCode("Test Faction")).thenReturn(faction);

    SpaceShip spaceShip = new SpaceShip();
    spaceShip.setId(1L);
    spaceShip.setName("Test Ship");
    spaceShip.setDescription("Test Description");
    spaceShip.setFaction(faction);
    spaceShip.setCreatedAt(LocalDateTime.now());
    spaceShip.setActive(true);

    when(spaceShipRepositoryMock.save(any(SpaceShip.class))).thenReturn(spaceShip);

    ResponseEntity<SpaceShipResponse> response = spaceShipService.createSpaceShipTask(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Test Ship", response.getBody().getName());
    assertEquals("Test Faction Description", response.getBody().getFaction());
  }

  @Test
  void testCreateSpaceShipTask_Exception() {
    SpaceShipRequest request = new SpaceShipRequest();
    request.setName("Test Ship");
    request.setDescription("Test Description");
    request.setFaction("Test Faction");

    when(factionServiceMock.getFactionByCode("Test Faction"))
        .thenThrow(new FactionNotFoundException("Facción no encontrada"));

    ResponseEntity<SpaceShipResponse> response = spaceShipService.createSpaceShipTask(request);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }


}
