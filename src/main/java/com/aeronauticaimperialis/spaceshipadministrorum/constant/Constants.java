package com.aeronauticaimperialis.spaceshipadministrorum.constant;

import com.aeronauticaimperialis.spaceshipadministrorum.enums.AuditTypes;

public class Constants {
  
  // Roles
  public static final String USER_ROLE = "USER";
  public static final String ADMIN_ROLE = "ADMIN";
  
  // Mensajes respuesta para peticiones HTTP de creación de usuario
  public static final String CREATION_MESSAGE = "Usuario %s creado con éxito";
  public static final String WELCOME_MESSAGE = "¡Bienvenido, %s! Que el Emperador ilumine vuestro camino en esta galaxia sombría.";
  public static final String WELCOME_ADMIN_MESSAGE = "¡Oh venerado %s, líder incuestionable de nuestras huestes! Que la luz del Emperador ilumine cada uno de tus pasos en esta vasta galaxia.";
  
  // Mensajes de auditoría de usuarios para KAFKA
  public static final String TOPIC_AUDIT_CREATE_USER_EVENT_MESSAGE = "Creacion de usuario %s";
  public static final String TOPIC_AUDIT_CREATE_USER_EVENT_DESCRIPTION_MESSAGE = "Creado con rol : %s";
  
  // Mensajes de creación de nave para KAFKA
  public static final String CREATING_SPACE_SHIP_MESSAGE = "Creación de nave %s, exitoso.";
  public static final String CREATING_SPACE_SHIP_DESCRIPTION_MESSAGE = "Cumpliendo con la siguiente descripción: %s \n y sirviendo al bando: %s";
  

}
