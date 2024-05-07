package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.request.UserCreationRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.UserCreationResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

  private final UserDetailService userDetailService;
  
  public RegistrationController(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }
  
  @PostMapping("/user")
  public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequest userRequest) {
    return userDetailService.createUser(userRequest);
  }

}
