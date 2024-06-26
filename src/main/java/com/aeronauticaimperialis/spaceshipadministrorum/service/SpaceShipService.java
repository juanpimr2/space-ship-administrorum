package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.SpaceShipNotFoundException;
import com.aeronauticaimperialis.spaceshipadministrorum.exception.SpaceShipServiceException;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.SpaceShipRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.SpaceShipResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceShipService {

  private final SpaceShipRepository spaceShipRepository;
  private final FactionService factionService;

  public SpaceShipService(SpaceShipRepository spaceShipRepository, FactionService factionService) {
    this.spaceShipRepository = spaceShipRepository;
    this.factionService = factionService;
  }

  /**
   * Crea una nueva nave espacial para el glorioso Imperio.
   *
   * @param solicitudNaveEspacial El objeto de solicitud que contiene los detalles de la nave
   *        espacial.
   * @return ResponseEntity con los detalles de la nave espacial creada.
   */
  @Transactional
  public ResponseEntity<SpaceShipResponse> createSpaceShipTask(
      SpaceShipRequest solicitudNaveEspacial) {
    try {
      log.info(
          "Empezando createSpaceShipTask(SpaceShipRequest solicitudNaveEspacial) || solicitudNaveEspacial: {}",
          solicitudNaveEspacial);
      SpaceShip spaceShip = new SpaceShip();
      // Copiar propiedades de la solicitud a la entidad
      BeanUtils.copyProperties(solicitudNaveEspacial, spaceShip);
      spaceShip.setFaction(factionService.getFactionByCode(solicitudNaveEspacial.getFaction()));
      spaceShip.setCreatedAt(LocalDateTime.now());
      spaceShip.setUpdatedAt(null);
      spaceShip.setActive(true);
      // Guardar la entidad en la base de datos
      log.info("Guardando {} en repositorio...", spaceShip);
      spaceShip = spaceShipRepository.save(spaceShip);
      log.info("Guardado finalizado con exito");
      log.info("Mapeando respuesta...");
      SpaceShipResponse result = new SpaceShipResponse();
      BeanUtils.copyProperties(spaceShip, result);
      result.setFaction(spaceShip.getFaction().getDescription());
      // Devolver ResponseEntity con estado de éxito y detalles de la nave espacial creada. ¡GLORIA
      // EL IMPERIO DE LA HUMANIDAD!
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      // Registrar error
      log.error("Se produjo un error al crear la nave espacial: {}", e.getMessage());
      // Devolver ResponseEntity con estado de error
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  /**
   * Recupera una lista paginada de todas las naves espaciales.
   *
   * @param pageNumber El número de página a recuperar.
   * @param pageSize El tamaño de la página.
   * @return ResponseEntity con la lista paginada de naves espaciales.
   */
  public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShip(int pageNumber, int pageSize) {
    try {
      log.info("Iniciando getAllSpaceShip");

      // Crear objeto Pageable para paginación
      Pageable pageable = PageRequest.of(pageNumber, pageSize);

      // Recuperar página de naves espaciales desde el repositorio
      Page<SpaceShip> spaceShipPage = spaceShipRepository.findAll(pageable);

      // Mapear las naves espaciales a SpaceShipResponse
      List<SpaceShipResponse> spaceShipResponses =
          spaceShipPage.getContent().stream().map(spaceShip -> {
            log.info("Mapeando: {}", spaceShip);
            SpaceShipResponse response = new SpaceShipResponse();
            BeanUtils.copyProperties(spaceShip, response);
            response.setFaction(spaceShip.getFaction().getDescription()); // Asignar el nombre de la
                                                                          // facción
            return response;
          }).toList();

      // Devolver ResponseEntity con estado de éxito y lista de naves espaciales paginadas
      log.info("Finalizando getAllSpaceShip");
      return ResponseEntity.ok(spaceShipResponses);
    } catch (Exception e) {
      // Capturar y manejar cualquier excepción ocurrida durante la recuperación de las naves
      // espaciales
      log.error("Error al recuperar las naves espaciales: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


  /**
   * Recupera una nave espacial por su ID.
   *
   * @param id El ID de la nave espacial a recuperar.
   * @return ResponseEntity con los detalles de la nave espacial.
   * @throws SpaceShipNotFoundException Si la nave espacial no se encuentra.
   * @throws SpaceShipServiceException Si ocurre un error al recuperar la nave espacial.
   */
  @Cacheable(value = "spaceShipCache", key = "#id")
  public ResponseEntity<SpaceShipResponse> getSpaceShipById(Long id) {
    try {
      log.info("Iniciando getSpaceShipById para el ID: {}", id);

      // Buscar la nave espacial por su ID
      SpaceShip spaceShip = spaceShipRepository.findById(id).orElse(null);

      // Verificar si la nave espacial existe
      if (spaceShip != null) {
        // Mapear la nave espacial a la respuesta
        SpaceShipResponse response = new SpaceShipResponse();
        BeanUtils.copyProperties(spaceShip, response);
        response.setFaction(spaceShip.getFaction().getDescription()); // Asignar el nombre de la
                                                                      // facción
        log.info("Nave espacial encontrada: {}", response);
        // Devolver ResponseEntity con estado de éxito y detalles de la nave espacial
        return ResponseEntity.ok(response);
      } else {
        // Lanzar excepción SpaceShipNotFoundException si la nave espacial no se encuentra
        throw new SpaceShipNotFoundException(String.format(Constants.SHIP_BY_ID_NOT_FOUND, id));
      }
    } catch (Exception e) {
      // Capturar y manejar cualquier excepción ocurrida durante la recuperación de la nave espacial
      log.error("Error al obtener la nave espacial con ID {}: {}", id, e.getMessage());
      // Lanzar excepción SpaceShipServiceException si ocurre una excepción
      throw new SpaceShipServiceException(
          "Error al obtener la nave espacial con ID: " + id + " error: " + e);
    }
  }



  /**
   * Busca naves espaciales por nombre.
   *
   * @param name El nombre a buscar.
   * @return ResponseEntity con la lista de naves espaciales encontradas.
   * @throws SpaceShipNotFoundException Si no se encuentran naves espaciales con el nombre
   *         proporcionado.
   * @throws SpaceShipServiceException Si ocurre un error durante la búsqueda.
   */
  public ResponseEntity<List<SpaceShipResponse>> searchSpaceShipsByName(String name) {
    try {
      List<SpaceShip> spaceShips = spaceShipRepository.findByNameContainingIgnoreCase(name);
      if (spaceShips.isEmpty()) {
        throw new SpaceShipNotFoundException(
            "No se encontraron naves espaciales con el nombre proporcionado");
      }
      List<SpaceShipResponse> spaceShipResponses =
          spaceShips.stream().map(this::mapSpaceShipToSpaceShipResponse).toList();
      return ResponseEntity.ok(spaceShipResponses);
    } catch (Exception e) {
      throw new SpaceShipServiceException(
          "Error al buscar naves espaciales por nombre, error: " + e);
    }
  }

  /**
   * Actualiza una nave espacial.
   *
   * @param id El ID de la nave espacial a actualizar.
   * @param spaceShipRequest La solicitud de la nave espacial actualizada.
   * @return ResponseEntity con los detalles de la nave espacial actualizada.
   * @throws SpaceShipNotFoundException Si la nave espacial no se encuentra.
   * @throws SpaceShipServiceException Si ocurre un error durante la actualización.
   */
  public ResponseEntity<SpaceShipResponse> updateSpaceShip(Long id,
      SpaceShipRequest spaceShipRequest) {
    try {
      log.info("Iniciando updateSpaceShip para el ID: {}", id);
      Optional<SpaceShip> optionalSpaceShip = spaceShipRepository.findById(id);
      if (optionalSpaceShip.isPresent()) {
        SpaceShip spaceShip = optionalSpaceShip.get();
        BeanUtils.copyProperties(spaceShipRequest, spaceShip);
        spaceShip.setFaction(factionService.getFactionByCode(spaceShipRequest.getFaction()));
        spaceShip.setUpdatedAt(LocalDateTime.now());
        SpaceShip updatedSpaceShip = spaceShipRepository.save(spaceShip);
        SpaceShipResponse response = mapSpaceShipToSpaceShipResponse(updatedSpaceShip);
        log.info("Nave espacial actualizada: {}", response);
        return ResponseEntity.ok(response);
      } else {
        log.warn("Nave espacial con ID {} no encontrada", id);
        throw new SpaceShipNotFoundException(String.format(Constants.SHIP_BY_ID_NOT_FOUND, id));
      }
    } catch (Exception e) {
      log.error("Error al actualizar la nave espacial con ID {}: {}", id, e.getMessage());
      throw new SpaceShipServiceException(
          "Error al actualizar la nave espacial con ID: " + id + ". Error: " + e);
    }
  }

  /**
   * Elimina una nave espacial por su ID.
   *
   * @param id El ID de la nave espacial a eliminar.
   * @return ResponseEntity con estado de éxito si la eliminación fue exitosa.
   * @throws SpaceShipNotFoundException Si la nave espacial no se encuentra.
   * @throws SpaceShipServiceException Si ocurre un error durante la eliminación.
   */
  public ResponseEntity<Void> deleteSpaceShip(Long id) {
    try {
      log.info("Iniciando deleteSpaceShip para el ID: {}", id);
      Optional<SpaceShip> optionalSpaceShip = spaceShipRepository.findById(id);
      if (optionalSpaceShip.isPresent()) {
        spaceShipRepository.deleteById(id);
        log.info("Nave espacial con ID {} eliminada correctamente", id);
        return ResponseEntity.noContent().build();
      } else {
        log.warn("Nave espacial con ID {} no encontrada", id);
        throw new SpaceShipNotFoundException(String.format(Constants.SHIP_BY_ID_NOT_FOUND, id));
      }
    } catch (Exception e) {
      log.error("Error al eliminar la nave espacial con ID {}: {}", id, e.getMessage());
      throw new SpaceShipServiceException(
          "Error al eliminar la nave espacial con ID: " + id + ". Error: " + e);
    }
  }

  /**
   * Mapea una instancia de SpaceShip a SpaceShipResponse.
   *
   * @param spaceShip La nave espacial a ser mapeada.
   * @return SpaceShipResponse mapeado desde la nave espacial.
   */
  private SpaceShipResponse mapSpaceShipToSpaceShipResponse(SpaceShip spaceShip) {
    SpaceShipResponse response = new SpaceShipResponse();
    response.setId(spaceShip.getId());
    response.setName(spaceShip.getName());
    response.setDescription(spaceShip.getDescription());
    response.setFaction(spaceShip.getFaction().getDescription());
    return response;
  }


  @CacheEvict(value = "spaceShipCache", allEntries = true)
  public void clearCache() {
    log.info("Limpiando Cache...");
  }
}
