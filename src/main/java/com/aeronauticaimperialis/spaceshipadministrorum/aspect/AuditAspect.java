package com.aeronauticaimperialis.spaceshipadministrorum.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.AuditMessage;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.TopicEnum;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;
import com.aeronauticaimperialis.spaceshipadministrorum.utils.UtilsImperatoris;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AuditAspect {



  private final AuditService auditService;

  public AuditAspect(AuditService auditService) {
    this.auditService = auditService;
  }

  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository.save(..)) && args(userDetail)")
  public void saveUserOperation(UserDetail userDetail) {}
  
  // Punto de corte para capturar las operaciones de guardado de naves espaciales
  @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.repository.SpaceShipRepository.save(..)) && args(spaceShip)")
  public void saveSpaceShipOperation(SpaceShip spaceShip) {}

  @AfterReturning(pointcut = "saveUserOperation(userDetail)", returning = "result")
  public void afterSaveUser(JoinPoint joinPoint, UserDetail userDetail, Object result) {
      AuditMessage auditMessage = new AuditMessage();
      // Crear objeto AuditMessageModel con los datos relevantes
      auditMessage.setEvent(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_MESSAGE, userDetail.getUsername()));
      auditMessage.setEventDescription(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE, userDetail.getRole()));
      auditMessage.setEventType(AuditTypes.CREATE_USER.getType() + ": " + AuditTypes.CREATE_USER.getDescription());
      auditMessage.setTimestamp(LocalDateTime.now());
      // Enviar el mensaje de auditoría con los datos del usuario
      auditService.enviarMensajeDeAuditoria(TopicEnum.USERS_TOPIC.getTopic(), auditMessage);
  }
  
  // Método de aspecto para realizar acciones después de guardar una nave espacial
  @AfterReturning(pointcut = "saveSpaceShipOperation(spaceShip)", returning = "result")
  public void afterSaveSpaceShip(JoinPoint joinPoint, SpaceShip spaceShip, Object result) {
      // Comentar el código aquí para estructurarlo adecuadamente
      
      AuditMessage auditMessage = new AuditMessage();
      // Crear objeto AuditMessage con los datos relevantes
      auditMessage.setEvent(String.format(Constants.CREATING_SPACE_SHIP_MESSAGE, spaceShip.getName()));
      auditMessage.setEventDescription(String.format(Constants.CREATING_SPACE_SHIP_DESCRIPTION_MESSAGE,spaceShip.getDescription(),spaceShip.getFaction().getCode()));
      auditMessage.setEventType(AuditTypes.CREATE_SPACE_SHIP.getType() + ": " + AuditTypes.CREATE_SPACE_SHIP.getDescription());
      auditMessage.setTimestamp(LocalDateTime.now());
      // Enviar el mensaje de auditoría con los datos de la nave espacial
      auditService.enviarMensajeDeAuditoria(TopicEnum.SPACE_SHIP_TOPIC.getTopic(), auditMessage);
  }
}
