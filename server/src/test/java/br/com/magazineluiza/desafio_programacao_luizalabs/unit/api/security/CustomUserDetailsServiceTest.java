package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.security;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = new CustomUserDetailsService();
    }

    @Test
    void loadUserByUsername_ExistingUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        assertNotNull(userDetails);
        assertEquals("user1", userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_ExistingAdmin() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_NonExistingUser() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistentuser");
        });
    }

    @Test
    void loadUserByUsername_CorrectPassword() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user1");
        assertEquals("$2a$12$74qc9RB/33wyGlvnic3bu.5SYYTnKnFpDf.lC7ZRwaHOAAnuLui7K", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_CaseSensitive() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("User1");
        });
    }
}
