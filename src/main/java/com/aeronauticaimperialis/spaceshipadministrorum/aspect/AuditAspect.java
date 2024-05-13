package com.aeronauticaimperialis.spaceshipadministrorum.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.AuditMessage;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.TopicEnum;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class AuditAspect {



  private final AuditService auditService;

  public AuditAspect(AuditService auditService) {
    this.auditService = auditService;
  }
  // Pointcut para la operación de guardado de usuario
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository.save(..)) && args(userDetail)")
  public void saveUserOperation(UserDetail userDetail) {}

  // Pointcut para la operación de creacion de una nave espacial
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.createSpaceShipTask(..)) && args(solicitudNaveEspacial)")
  public void saveSpaceShipOperation(SpaceShipRequest solicitudNaveEspacial) {}

  // Pointcut para la operación de modificación de una nave espacial
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.updateSpaceShip(..)) && args(id, spaceShipRequest)")
  public void updateSpaceShipPointcut(Long id, SpaceShipRequest spaceShipRequest) {}
  
  // Pointcut para la operación de borrado de una nave espacial
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.deleteSpaceShip(..)) && args(id)")
  public void deleteSpaceShipPointcut(Long id) {}

  // Pointcut para la operación de consulta de todas las naves espaciales
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.getAllSpaceShip(..)) && args(pageNumber, pageSize)")
  public void getAllSpaceShipPointcut(int pageNumber, int pageSize) {}

  // Pointcut para la operación de consulta de naves espaciales por su ID de bbdd
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.getSpaceShipById(..)) && args(id)")
  public void getSpaceShipByIdPointcut(Long id) {}

  // Pointcut para la operación de consulta de naves espaciales por su nombre
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.searchSpaceShipsByName(..)) && args(name)")
  public void searchSpaceShipsByNamePointcut(String name) {}

  
  // Metodo para después de la operación de guardado de usuario
  @AfterReturning(pointcut = "saveUserOperation(userDetail)", returning = "result")
  public void afterSaveUser(JoinPoint joinPoint, UserDetail userDetail, Object result) {
    auditService.enviarMensajeDeAuditoria(TopicEnum.USERS_TOPIC.getTopic(), getCurrentUsername(),
        buildAuditMessage(AuditTypes.CREATE_USER,
            String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE,
                userDetail.getUsername(), userDetail.getRole())));
  }

  
  // Metodo para después de la operación de creacion de una nave espacial
  @AfterReturning(pointcut = "saveSpaceShipOperation(spaceShip)", returning = "result")
  public void afterSaveSpaceShip(JoinPoint joinPoint, SpaceShipRequest spaceShip, Object result) {
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(), buildAuditMessage(AuditTypes.CREATE_SPACE_SHIP, String.format(
            Constants.CREATING_SPACE_SHIP_MESSAGE, spaceShip.getName(), spaceShip.getFaction())));
  }


  // Metodo para después de la operación de modificación de una nave espacial
  @AfterReturning(pointcut = "updateSpaceShipPointcut(id, spaceShipRequest)", returning = "result")
  public void afterUpdateSpaceShip(JoinPoint joinPoint, Long id, SpaceShipRequest spaceShipRequest,
      Object result) {
    log.info("Auditing updateSpaceShip...");
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(),
        buildAuditMessage(AuditTypes.MODIFY_SPACE_SHIP, "Modificando nave con ID: " + id));
    
  }

  // Metodo para después de la operación de borrado de una nave espacial
  @AfterReturning(pointcut = "deleteSpaceShipPointcut(id)", returning = "result")
  public void afterDeleteSpaceShip(JoinPoint joinPoint, Long id, Object result) {
    log.info("Auditing deleteSpaceShip...");
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(),
        buildAuditMessage(AuditTypes.DELETE_SPACE_SHIP, "Borrando de nave con ID: " + id));
  }

  // Metodo para después de la operación de consulta todas las naves espaciales
  @AfterReturning(pointcut = "getAllSpaceShipPointcut(pageNumber, pageSize)", returning = "result")
  public void afterGetAllSpaceShip(JoinPoint joinPoint, int pageNumber, int pageSize,
      Object result) {
    log.info("Auditing getAllSpaceShip...");
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(),
        buildAuditMessage(AuditTypes.GET_SPACE_SHIP, "Consultade todas las naves"));
  }

  // Metodo para después de la operación de consulta de una nave espacial por su ID
  @AfterReturning(pointcut = "getSpaceShipByIdPointcut(id)", returning = "result")
  public void afterGetSpaceShipById(JoinPoint joinPoint, Long id, Object result) {
    log.info("Auditing getSpaceShipById...");
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(),
        buildAuditMessage(AuditTypes.GET_SPACE_SHIP, "Consulta de nave con ID: " + id));
  }

  // Metodo para después de la operación de consulta de una nave espacial por su nombre
  @AfterReturning(pointcut = "searchSpaceShipsByNamePointcut(name)", returning = "result")
  public void afterSearchSpaceShipsByName(JoinPoint joinPoint, String name, Object result) {
    log.info("Auditing searchSpaceShipsByName...");
    auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(),
        getCurrentUsername(),
        buildAuditMessage(AuditTypes.GET_SPACE_SHIP, "Buscando nave con nombre: " + name));
  }


  /**
   * Obtiene el nombre de usuario actual desde el contexto de seguridad.
   * @return El nombre de usuario actual.
   */
  private String getCurrentUsername() {
    log.info("Inciando getCurrentUsername()");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      return authentication.getName();
    }
    return null;
  }


  /**
   * Construye un mensaje de auditoría.
   * @param type Tipo de auditoría.
   * @param description Descripción de la auditoría.
   * @return El mensaje de auditoría construido.
   */
  private AuditMessage buildAuditMessage(AuditTypes type, String description) {
    AuditMessage auditMessage = new AuditMessage();
    auditMessage.setEvent(type.getEvent());
    auditMessage.setEventDescription(description);
    auditMessage.setEventType(type.getType());
    auditMessage.setTimestamp(LocalDateTime.now());
    return auditMessage;
  }

}
