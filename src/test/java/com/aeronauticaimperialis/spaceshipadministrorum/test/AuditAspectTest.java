package com.aeronauticaimperialis.spaceshipadministrorum.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import com.aeronauticaimperialis.spaceshipadministrorum.aspect.AuditAspect;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.AuditMessage;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;
import com.aeronauticaimperialis.spaceshipadministrorum.request.SpaceShipRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;

@ActiveProfiles("test")
public class AuditAspectTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditAspect auditAspect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAfterSaveUser() {
        // Given
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("testUser");
        userDetail.setRole("admin");
        JoinPoint joinPoint = null;
        Object result = null;

        // When
        auditAspect.afterSaveUser(joinPoint, userDetail, result);

        // Then
        AuditMessage expectedMessage = new AuditMessage();
        expectedMessage.setEvent(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE, userDetail.getUsername(),userDetail.getRole()));
        expectedMessage.setEventDescription(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE, userDetail.getUsername(),userDetail.getRole()));
        expectedMessage.setEventType(AuditTypes.CREATE_USER.getType() + ": " + AuditTypes.CREATE_USER.getEvent());
        expectedMessage.setTimestamp(LocalDateTime.now());
        verify(auditService, times(1)).enviarMensajeDeAuditoria(any(), any(), any());
    }

    @Test
    public void testAfterSaveSpaceShip() {
        // Given
        SpaceShipRequest spaceShip = new SpaceShipRequest();
        spaceShip.setName("testSpaceShip");
        spaceShip.setDescription("Test description");
        spaceShip.setFaction("IP");

        JoinPoint joinPoint = null;
        Object result = null;

        // When
        auditAspect.afterSaveSpaceShip(joinPoint, spaceShip, result);

        // Then
        AuditMessage expectedMessage = new AuditMessage();
        expectedMessage.setEvent(String.format(Constants.CREATING_SPACE_SHIP_MESSAGE, spaceShip.getName(), spaceShip.getFaction()));
        expectedMessage.setEventDescription(String.format(Constants.CREATING_SPACE_SHIP_MESSAGE, spaceShip.getDescription(), spaceShip.getFaction()));
        expectedMessage.setEventType(AuditTypes.CREATE_SPACE_SHIP.getType() + ": " + AuditTypes.CREATE_SPACE_SHIP.getEvent());
        expectedMessage.setTimestamp(LocalDateTime.now());
        verify(auditService, times(1)).enviarMensajeDeAuditoria(any(), any(), any());
    }
}
