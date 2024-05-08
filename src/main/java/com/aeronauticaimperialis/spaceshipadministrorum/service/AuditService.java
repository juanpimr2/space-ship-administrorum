package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.model.AuditMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditService {
    private static final String TOPIC = "auditoria-topic";
    @Autowired
    private KafkaTemplate<String,Object> template;

    public void enviarMensajeDeAuditoria(AuditMessage auditMessage) {
      try {
        CompletableFuture<SendResult<String, Object>> future = template.send(TOPIC, auditMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + auditMessage.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                    auditMessage.toString() + "] due to : " + ex.getMessage());
            }
        });

    } catch (Exception ex) {
        System.out.println("ERROR : "+ ex.getMessage());
    }
}

    @KafkaListener(topics = "auditoria-topic", groupId = "space-ship-group")
    public void listen(AuditMessage auditMessage) {
      log.info("consumer consume the events {} ", auditMessage.toString());
    }


}
