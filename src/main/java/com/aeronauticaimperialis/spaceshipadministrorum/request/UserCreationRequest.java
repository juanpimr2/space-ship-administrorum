package com.aeronauticaimperialis.spaceshipadministrorum.request;

import lombok.Data;

@Data
public class UserCreationRequest {
  private String username;
  private String password;

}
