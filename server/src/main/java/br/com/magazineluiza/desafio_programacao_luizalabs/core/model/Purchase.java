package br.com.magazineluiza.desafio_programacao_luizalabs.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String purchaserName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private Integer purchaseCount;
    private String merchantAddress;
    private String merchantName;
    private UUID correlationId;
    private LocalDateTime inclusionDate;

    @PrePersist
    protected void onCreate() {
        inclusionDate = LocalDateTime.now();
    }

}
