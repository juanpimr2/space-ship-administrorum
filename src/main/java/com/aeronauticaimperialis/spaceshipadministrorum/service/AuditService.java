package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.component.AuditMessageStore;

@Service
public class AuditService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AuditMessageStore auditMessageStore;

    @Autowired
    public AuditService(KafkaTemplate<String, String> kafkaTemplate, AuditMessageStore auditMessageStore) {
        this.kafkaTemplate = kafkaTemplate;
        this.auditMessageStore = auditMessageStore;
    }

    public void enviarMensajeDeAuditoria(String mensaje) {
        this.kafkaTemplate.send("auditoria-topic", mensaje);
    }

    @KafkaListener(topics = "auditoria-topic", groupId = "space-ship-group")
    public void listen(String message) {
        auditMessageStore.addAuditMessage(message);
    }

    public List<String> getAuditMessages() {
        return auditMessageStore.getAuditMessages();
    }
}
