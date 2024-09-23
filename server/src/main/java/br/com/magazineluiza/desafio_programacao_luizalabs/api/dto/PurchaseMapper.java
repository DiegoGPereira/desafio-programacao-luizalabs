package br.com.magazineluiza.desafio_programacao_luizalabs.api.dto;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PurchaseMapper {

    @Mapping(source = "item.description", target = "itemDescription")
    @Mapping(source = "item.price", target = "itemPrice")
    @Mapping(source = "merchant.name", target = "merchantName")
    @Mapping(source = "merchant.address", target = "merchantAddress")
    @Mapping(source = "purchaser.name", target = "purchaserName")
    @Mapping(source = "purchase.purchaseCount", target = "purchaseCount")
    @Mapping(source = "purchaseFile.inclusionDate", target = "inclusionDate")
    PurchaseResponse toPurchaseResponse(Purchase purchase);

}
