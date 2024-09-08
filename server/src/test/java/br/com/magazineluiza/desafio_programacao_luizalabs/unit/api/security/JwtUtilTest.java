package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.security;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "testetestetesteteste123@testetestetesteteste123");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 3600000L); // 1 hour

        userDetails = new User("testuser", "password", new ArrayList<>());
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void extractUsername() {
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }

    @Test
    void extractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void extractClaim() {
        String subject = jwtUtil.extractClaim(token, Claims::getSubject);
        assertEquals("testuser", subject);
    }

    @Test
    void generateToken() {
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateToken_validToken() {
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void validateToken_invalidUsername() {
        UserDetails invalidUser = new User("qualqueruser", "password", new ArrayList<>());
        assertFalse(jwtUtil.validateToken(token, invalidUser));
    }

    @Test
    void validateToken_expiredToken() {
        ReflectionTestUtils.setField(jwtUtil, "expiration", -3600000L);
        String expiredToken = jwtUtil.generateToken(userDetails);
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(expiredToken, userDetails));
    }
}
