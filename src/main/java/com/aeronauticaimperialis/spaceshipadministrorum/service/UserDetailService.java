package com.aeronauticaimperialis.spaceshipadministrorum.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.aeronauticaimperialis.spaceshipadministrorum.model.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.request.UserCreationRequest;
import com.aeronauticaimperialis.spaceshipadministrorum.response.UserCreationResponse;


@Service
public class UserDetailService implements UserDetailsService {

    private static final String USER_ROLE = "USER";
    private static final String CREATION_MESSAGE = "Usuario %s creado con éxito";
    private static final String WELCOME_MESSAGE = "¡Bienvenido, %s! Que el Emperador ilumine vuestro camino en esta galaxia sombría.";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetail> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            var userObj = optionalUser.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(UserDetail user) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            return new String[] { USER_ROLE };
        }
        return user.getRole().split(",");
    }

    public ResponseEntity<UserCreationResponse> createUser(UserCreationRequest userRequest) {
        UserCreationResponse response = new UserCreationResponse();
        if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(userRequest.getUsername());
        userDetail.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userDetail.setRole(USER_ROLE);
        try {
            userRepository.save(userDetail);
            response.setDescription(String.format(CREATION_MESSAGE, userRequest.getUsername()));
            response.setMissive(String.format(WELCOME_MESSAGE, userRequest.getUsername()));
            response.setStatus(HttpStatus.OK.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
