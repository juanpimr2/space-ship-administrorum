package com.aeronauticaimperialis.spaceshipadministrorum.component;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuditMessageStore {
    private final List<String> auditMessages;

    public AuditMessageStore() {
        this.auditMessages = new ArrayList<>();
    }

    public void addAuditMessage(String message) {
        auditMessages.add(message);
    }

    public List<String> getAuditMessages() {
        return auditMessages;
    }
}
