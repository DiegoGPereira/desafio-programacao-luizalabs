package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PurchaseResponse(String purchaserName,
                               String itemDescription,
                               BigDecimal itemPrice,
                               Integer purchaseCount,
                               String merchantAddress,
                               String merchantName,
                               UUID correlationId,
                               LocalDateTime inclusionDate) {}
