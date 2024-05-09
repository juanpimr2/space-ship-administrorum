package com.aeronauticaimperialis.spaceshipadministrorum.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditMessage {
    private String event;
    private LocalDateTime timestamp;
    private String eventType;
    private String eventDescription; 
}
