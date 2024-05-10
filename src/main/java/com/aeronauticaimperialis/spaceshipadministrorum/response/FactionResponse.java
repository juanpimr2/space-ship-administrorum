package com.aeronauticaimperialis.spaceshipadministrorum.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FactionResponse {
  
  private String code;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean active;

}
