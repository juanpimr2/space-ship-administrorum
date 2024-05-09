package com.aeronauticaimperialis.spaceshipadministrorum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicEnum {
    USERS_TOPIC("audit-users-topic"),
    SPACE_SHIP_TOPIC("audit-space-ship-topic");

    private final String topic;
}
