package com.aeronauticaimperialis.spaceshipadministrorum.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditMessage {
    private String username;
    private LocalDateTime timestamp;
    private String eventType;
    private String eventDescription; 
}
