package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseFilesResponse(UUID correlationId,
                                    LocalDate inclusionDate,
                                    BigDecimal grossByCorrelationId,
                                    BigDecimal totalGross) {
}
