package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseFileResponse(String fileName,
                                   LocalDateTime inclusionDate,
                                   BigDecimal gross) {
}
