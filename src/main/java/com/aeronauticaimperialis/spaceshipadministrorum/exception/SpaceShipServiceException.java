package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SpaceShipServiceException extends RuntimeException {
  private static final long serialVersionUID = 8000106209525248346L;

  public SpaceShipServiceException(String message) {
    super(message);
  }
}
