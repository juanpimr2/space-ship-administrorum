package com.aeronauticaimperialis.spaceshipadministrorum.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;
import com.aeronauticaimperialis.spaceshipadministrorum.model.AuditMessage;
import com.aeronauticaimperialis.spaceshipadministrorum.model.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;

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

  @AfterReturning(pointcut = "saveUserOperation(userDetail)", returning = "result")
  public void afterSaveUser(JoinPoint joinPoint, UserDetail userDetail, Object result) {
      AuditMessage auditMessage = new AuditMessage();
      // Crear objeto AuditMessageModel con los datos relevantes
      auditMessage.setUsername(String.format("Creando usuario %s", userDetail.getUsername()));
      auditMessage.setEventDescription(AuditTypes.CREATE_USER.getDescription());
      auditMessage.setEventType(AuditTypes.CREATE_USER.getType());
      auditMessage.setTimestamp(LocalDateTime.now());
      // Enviar el mensaje de auditoría con los datos del usuario
      auditService.enviarMensajeDeAuditoria(auditMessage);
  }
}
