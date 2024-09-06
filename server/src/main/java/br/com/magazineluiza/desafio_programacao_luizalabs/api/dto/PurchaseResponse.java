package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseResponse {

    private String purchaserName;
    private String itemDescription;
    private BigDecimal itemPrice;
    private Integer purchaseCount;
    private String merchantAddress;
    private String merchantName;

}
