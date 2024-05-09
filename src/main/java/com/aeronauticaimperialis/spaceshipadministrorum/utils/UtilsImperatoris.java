package com.aeronauticaimperialis.spaceshipadministrorum.utils;

import org.springframework.stereotype.Component;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;

@Component
public class UtilsImperatoris {
  
  
  public  String[] getRoles(UserDetail user) {
    if (user.getRole() == null || user.getRole().isBlank()) {
        return new String[] { Constants.USER_ROLE };
    }
    return user.getRole().split(",");
}

}
