package com.aeronauticaimperialis.spaceshipadministrorum.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.aeronauticaimperialis.spaceshipadministrorum.constant.Constants;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.UserDetail;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.UserRepository;
import com.aeronauticaimperialis.spaceshipadministrorum.service.UserDetailService;
import com.aeronauticaimperialis.spaceshipadministrorum.utils.UtilsImperatoris;

class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UtilsImperatoris utilsImperatoris; // Mock for UtilsImperatoris
    
    @InjectMocks
    private UserDetailService userDetailService;

    @BeforeEach
    public void setUp() {
      MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String role = Constants.USER_ROLE;
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(username);
        userDetail.setPassword(password);
        userDetail.setRole(role);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userDetail));
        when(utilsImperatoris.getRoles(userDetail)).thenReturn(new String[] { Constants.USER_ROLE }); // Mocking the roles

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
