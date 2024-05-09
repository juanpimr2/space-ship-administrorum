package com.aeronauticaimperialis.spaceshipadministrorum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.request.UserCreationRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.UserCreationResponse;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserDetailController", description = "The app for registration of the members of the imperium")
@RestController
@RequestMapping("/register")
public class UserDetailController {

  private final UserDetailService userDetailService;
  
  public UserDetailController(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }
  
  @Operation(
      summary = "Fetch all plants",
      description = "fetches all plant entities and their data from data source")
@ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation")
})
@PostMapping(value = "/user", produces = "application/json")
  public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequest userRequest) {
    return userDetailService.createUser(userRequest);
  }
  
  @Operation(
      summary = "Fetch all plants",
      description = "fetches all plant entities and their data from data source")
@ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation")
})
  @PostMapping("/admin")
  public ResponseEntity<UserCreationResponse> createAdmin(@RequestBody UserCreationRequest userRequest) {
    return userDetailService.createAdmin(userRequest);
  }

}
