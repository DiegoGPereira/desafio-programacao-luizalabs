package br.com.magazineluiza.desafio_programacao_luizalabs.unit.core.model;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testNoArgsConstructor() {
        UserEntity user = new UserEntity();
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertFalse(user.isActive());
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        String username = "testuser";
        String password = "password123";
        boolean active = true;

        UserEntity user = new UserEntity(username, password, active);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertTrue(user.isActive());
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    void testAuthoritiesInitialization() {
        UserEntity user = new UserEntity();
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());

        user.getAuthorities().add("ROLE_USER");
        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains("ROLE_USER"));
    }
}
