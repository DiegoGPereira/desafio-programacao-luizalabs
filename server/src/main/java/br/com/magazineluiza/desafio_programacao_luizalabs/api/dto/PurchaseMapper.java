package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import org.mapstruct.Mapper;

@Mapper
public interface PurchaseMapper {

    PurchaseResponse toPurchaseResponse(Purchase purchase);

}