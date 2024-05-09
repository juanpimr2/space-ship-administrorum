package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpaceShipNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 3397588317297451045L;

  public SpaceShipNotFoundException(String message) {
    super(message);
  }
}
