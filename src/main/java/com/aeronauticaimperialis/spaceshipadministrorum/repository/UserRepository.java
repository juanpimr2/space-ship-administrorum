package com.aeronauticaimperialis.spaceshipadministrorum.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;

public interface UserRepository extends JpaRepository<UserDetail, Long>{
  
 Optional<UserDetail> findByUsername(String username);

}
