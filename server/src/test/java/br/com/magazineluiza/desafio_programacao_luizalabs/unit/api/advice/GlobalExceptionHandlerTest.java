package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.advice;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.advice.GlobalExceptionHandler;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.ErrorResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.PurchaseFileProcessingException;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.UnsupportedFileTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handlePurchaseFileProcessingException() {
        PurchaseFileProcessingException exception = new PurchaseFileProcessingException("erro");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handlePurchaseFileProcessingException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("erro", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void handleUnsupportedFileTypeException() {
        UnsupportedFileTypeException exception = new UnsupportedFileTypeException("pdf");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUnsupportedFileTypeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().message().contains("pdf"));
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void handleUsernameNotFoundException() {
        UsernameNotFoundException exception = new UsernameNotFoundException("erro");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUsernameNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("erro", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void handleDataIntegrityViolationException() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("erro");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleDataIntegrityViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("erro", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("erro");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("erro", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

}
