package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp,
                            String message,
                            String details) {
}
