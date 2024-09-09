package br.com.magazineluiza.desafio_programacao_luizalabs.api.advice;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.ErrorResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.PurchaseFileProcessingException;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.UnsupportedFileTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PurchaseFileProcessingException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseFileProcessingException(PurchaseFileProcessingException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), "");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedFileTypeException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedFileTypeException(UnsupportedFileTypeException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), "");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), "");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
