package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.AuditMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditService {
  
    private KafkaTemplate<String,Object> template;
    
    public AuditService(KafkaTemplate<String, Object> template) {
      this.template = template;
    }

    public void enviarMensajeDeAuditoria(String topic, String key, AuditMessage auditMessage) {
      try {
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, key, auditMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[ {} ] with offset=[ {} ]", auditMessage.toString(), result.getRecordMetadata().offset());
            } else {
              log.info("Unable to send message=[ {} ] due to : {}", 
                  auditMessage.toString(), ex.getMessage());
            }
        });

    } catch (Exception ex) {
        log.error("ERROR : "+ ex.getMessage());
    }
}

    @KafkaListener(topics = {"audit-users-topic", "audit-space-ship-topic"}, groupId = "space-ship-group")
    public void listen(AuditMessage auditMessage) {
      log.info("consumer consume the events {} ", auditMessage.toString());
    }
}
