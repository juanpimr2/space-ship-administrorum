package com.aeronauticaimperialis.spaceshipadministrorum;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.aeronauticaimperialis.spaceshipadministrorum.model.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;

class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserDetailService userDetailService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailService = new UserDetailService(userRepository, passwordEncoder);
    }

    @Test
    void testLoadUserByUsername() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String role = "ADMIN";
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(username);
        userDetail.setPassword(password);
        userDetail.setRole(role);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userDetail));

        // Act
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // Assert
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(role, userDetail.getRole());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act/Assert
        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
        assertEquals("nonExistentUser", exception.getMessage());
    }
}
