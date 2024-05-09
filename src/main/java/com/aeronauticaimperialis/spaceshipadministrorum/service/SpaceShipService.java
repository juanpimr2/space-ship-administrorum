package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.SpaceShipRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.SpaceShipResponse;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceShipService {

    private static final Logger REGISTRO = LoggerFactory.getLogger(SpaceShipService.class);

    private final SpaceShipRepository spaceShipRepository;

    @Autowired
    public SpaceShipService(SpaceShipRepository spaceShipRepository) {
        this.spaceShipRepository = spaceShipRepository;
    }

    /**
     * Crea una nueva nave espacial para el glorioso Imperio.
     *
     * @param solicitudNaveEspacial El objeto de solicitud que contiene los detalles de la nave espacial.
     * @return ResponseEntity con los detalles de la nave espacial creada.
     */
    @Transactional
    public ResponseEntity<SpaceShipResponse> createSpaceShipTask(SpaceShipRequest solicitudNaveEspacial) {
        try {
            // Crear una nueva entidad SpaceShip
            SpaceShip spaceShip = new SpaceShip();
            // Copiar propiedades de la solicitud a la entidad
            BeanUtils.copyProperties(solicitudNaveEspacial, spaceShip);
            // Guardar la entidad en la base de datos
            spaceShip = spaceShipRepository.save(spaceShip);
            // Crear un objeto de respuesta
            SpaceShipResponse result = new SpaceShipResponse();
            // Copiar propiedades de la entidad a la respuesta
            BeanUtils.copyProperties(spaceShip, result);
            // Devolver ResponseEntity con estado de éxito y detalles de la nave espacial creada. ¡GLORIA EL IMPERIO DE LA HUMANIDAD!
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Registrar error
            REGISTRO.error("Se produjo un error al crear la nave espacial: {}", e.getMessage());
            // Devolver ResponseEntity con estado de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<List<SpaceShipResponse>> getAllSpaceShip(int pageNumber, int pageSize) {
      try {
        log.info("Iniciando getAllSpaceShip");
          // Configurar la paginación y ordenamiento
          Pageable pageable = PageRequest.of(pageNumber, pageSize);
          // Obtener la página de naves espaciales
          Page<SpaceShip> spaceShipPage = spaceShipRepository.findAll(pageable);
          // Mapear la página de naves espaciales a una lista de respuestas
          List<SpaceShipResponse> spaceShipResponses = spaceShipPage.getContent().stream()
                  .map(spaceShip -> {
                      log.info("Mapeando: {}", spaceShip);
                      SpaceShipResponse response = new SpaceShipResponse();
                      BeanUtils.copyProperties(spaceShip, response);
                      response.setFaction(spaceShip.getFaction().getName()); // Asignar el nombre de la facción
                      return response;
                  })
                  .toList();
          // Devolver ResponseEntity con estado de éxito y lista de naves espaciales paginadas
          log.info("Finalizando getAllSpaceShip");
          return ResponseEntity.ok(spaceShipResponses);
      } catch (Exception e) {
        log.error("Error: {}", e);
        log.info("Finalizando getAllSpaceShip");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
}
