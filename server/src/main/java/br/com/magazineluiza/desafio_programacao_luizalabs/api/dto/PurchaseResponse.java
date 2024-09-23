package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(String purchaserName,
                               String itemDescription,
                               BigDecimal itemPrice,
                               Integer purchaseCount,
                               String merchantAddress,
                               String merchantName,
                               LocalDateTime inclusionDate) {
}
