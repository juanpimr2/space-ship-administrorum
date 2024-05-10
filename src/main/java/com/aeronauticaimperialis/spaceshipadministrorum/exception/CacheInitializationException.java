package com.aeronauticaimperialis.spaceshipadministrorum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CacheInitializationException extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 4086192173812487210L;

  public CacheInitializationException(String message) {
      super(message);
  }
}
