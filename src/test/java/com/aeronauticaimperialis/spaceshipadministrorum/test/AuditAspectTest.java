package com.aeronauticaimperialis.spaceshipadministrorum.test;

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
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.SpaceShip;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;
import com.aeronauticaimperialis.spaceshipadministrorum.enums.TopicEnum;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;
import static org.mockito.ArgumentMatchers.any;

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
        expectedMessage.setEvent(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_MESSAGE, userDetail.getUsername()));
        expectedMessage.setEventDescription(String.format(Constants.TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE, userDetail.getRole()));
        expectedMessage.setEventType(AuditTypes.CREATE_USER.getType() + ": " + AuditTypes.CREATE_USER.getDescription());
        expectedMessage.setTimestamp(LocalDateTime.now());
        verify(auditService, times(1)).enviarMensajeDeAuditoria(TopicEnum.USERS_TOPIC.getTopic(), expectedMessage);
    }

    @Test
    public void testAfterSaveSpaceShip() {
        // Given
        SpaceShip spaceShip = new SpaceShip();
        spaceShip.setName("testSpaceShip");
        spaceShip.setDescription("Test description");
        
        // Mockear la facción
        Faction faction = new Faction();
        faction.setCode("testCode");
        spaceShip.setFaction(faction);

        JoinPoint joinPoint = null;
        Object result = null;

        // When
        auditAspect.afterSaveSpaceShip(joinPoint, spaceShip, result);

        // Then
        AuditMessage expectedMessage = new AuditMessage();
        expectedMessage.setEvent(String.format(Constants.CREATING_SPACE_SHIP_MESSAGE, spaceShip.getName()));
        expectedMessage.setEventDescription(String.format(Constants.CREATING_SPACE_SHIP_DESCRIPTION_MESSAGE, spaceShip.getDescription(), spaceShip.getFaction().getCode()));
        expectedMessage.setEventType(AuditTypes.CREATE_SPACE_SHIP.getType() + ": " + AuditTypes.CREATE_SPACE_SHIP.getDescription());
        expectedMessage.setTimestamp(LocalDateTime.now());
        verify(auditService, times(1)).enviarMensajeDeAuditoria(any(), any());
    }
}
