package com.aeronauticaimperialis.spaceshipadministrorum.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.service.AuditService;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/get-messages")
    public List<String> obtenerMensajesDeAuditoria() {
        return auditService.getAuditMessages();
    }
}
