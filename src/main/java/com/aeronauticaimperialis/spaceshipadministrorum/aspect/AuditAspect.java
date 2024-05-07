package com.aeronauticaimperialis.spaceshipadministrorum.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    @Autowired
    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Pointcut("execution(* com.aeronauticaimperialis.spaceshipadministrorum.repository.*Repository.save(..))")
    public void saveOperation() {}

    @AfterReturning(pointcut = "saveOperation()", returning = "result")
    public void afterSave(Object result) {
        auditService.enviarMensajeDeAuditoria("Se ha creado/actualizado un usuario");
    }

    // Añade otros métodos para capturar eventos de actualización y eliminación si es necesario
}
