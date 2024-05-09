package com.aeronauticaimperialis.spaceshipadministrorum.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NegativeIdLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(NegativeIdLoggingAspect.class);

    @Before("execution(* com.aeronauticaimperialis.spaceshipadministrorum.service.SpaceShipService.getSpaceShipById(..)) && args(id,..)")
    public void logNegativeIdRequest(JoinPoint joinPoint, Long id) {
        if (id < 0) {
            logger.warn("Se ha solicitado una nave con un ID negativo: {}", id);
        }
    }
}
