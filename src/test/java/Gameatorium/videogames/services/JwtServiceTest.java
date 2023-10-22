package Gameatorium.videogames.services;

import Gameatorium.videogames.configurations.JwtUtil;
import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.JwtRequest;
import Gameatorium.videogames.models.JwtResponse;
import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    private Users testUser;
    private JwtRequest testJwtRequest;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testUser = new Users();
        testUser.setUsername("testuser");
        testUser.setEmailId("testuser@example.com");
        testUser.setPassword("testpassword");
        testUser.setRoles(new HashSet<>());

        testJwtRequest = new JwtRequest();
        testJwtRequest.setUsername("testuser");
        testJwtRequest.setPassword("testpassword");
    }

    @Test
    @DisplayName("Load User by Username or Email")
    void loadUserByUsername() {
        Mockito.when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(Optional.of(testUser));

        UserDetails userDetails = jwtService.loadUserByUsernameInternal("testuser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof User);

        User user = (User) userDetails;
        assertEquals("testuser@example.com", user.getUsername());
        assertEquals("testpassword", user.getPassword());

//        List<GrantedAuthority> authorities = user.getAuthorities();
//        assertEquals(0, authorities.size());
    }

    @Test
    @DisplayName("Create JWT Token")
    void createJwtToken() throws Exception {
        Mockito.when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(Optional.of(testUser));
        Mockito.when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("generatedToken");

        JwtResponse jwtResponse = jwtService.createJwtToken(testJwtRequest);

        assertNotNull(jwtResponse);
        assertEquals("generatedToken", jwtResponse.getJwtToken());
    }

    @Test
    @DisplayName("Authenticate User")
    void authenticateUser() {
        Mockito.when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(Optional.of(testUser));
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        assertDoesNotThrow(() -> jwtService.authenticate("testuser", "testpassword"));
    }

    @Test
    @DisplayName("Authenticate User - DisabledException")
    void authenticateUserDisabledException() {
        Mockito.when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(Optional.of(testUser));
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new DisabledException("User is disabled"));

        assertThrows(Exception.class, () -> jwtService.authenticate("testuser", "testpassword"));
    }

    @Test
    @DisplayName("Authenticate User - UsernameNotFoundException")
    void authenticateUserUsernameNotFoundException() {
        Mockito.when(userRepository.findByUsernameOrEmail(anyString())).thenReturn(Optional.of(testUser));
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("Bad credentials from user"));

        assertThrows(UserNotFoundException.class, () -> jwtService.authenticate("testuser", "testpassword"));
    }
}

