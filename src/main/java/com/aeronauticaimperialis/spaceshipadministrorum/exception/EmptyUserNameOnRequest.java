package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyUserNameOnRequest extends Exception {
  private static final long serialVersionUID = 8076553814094291971L;

  public EmptyUserNameOnRequest (String message) {
    super(message);
  }

}
