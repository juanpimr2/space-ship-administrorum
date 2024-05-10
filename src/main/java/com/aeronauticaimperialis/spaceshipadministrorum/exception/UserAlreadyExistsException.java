package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends Exception {
  private static final long serialVersionUID = 1826284598020993931L;
  public UserAlreadyExistsException(String message) {
    super(message);
  }

}
