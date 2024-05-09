package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FactionNotFoundException extends RuntimeException{
  

  private static final long serialVersionUID = 8976411948575199256L;

  public FactionNotFoundException(String message) {
    super(message);
  }

}
