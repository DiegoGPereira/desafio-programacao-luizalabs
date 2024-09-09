package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.controller.AuthController;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.AuthenticationRequest;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.AuthenticationResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Test
    void createAuthenticationToken_Success() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", "password");
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String jwtToken = "jwt.token";

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(jwtToken);

        ResponseEntity<?> response = authController.createAuthenticationToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(AuthenticationResponse.class, response.getBody());
        assertEquals(jwtToken, ((AuthenticationResponse) response.getBody()).jwt());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername("testuser");
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    void createAuthenticationToken_Failure() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", "wrongpassword");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("erro"));

        ResponseEntity<?> response = authController.createAuthenticationToken(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("erro"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userDetailsService);
        verifyNoInteractions(jwtUtil);
    }
}