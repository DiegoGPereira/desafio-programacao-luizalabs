package br.com.magazineluiza.desafio_programacao_luizalabs.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseMapper;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileServiceFactory;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseFileServiceFactory purchaseFileServiceFactory;

    @GetMapping("/{fileName}")
    public ResponseEntity<List<PurchaseResponse>> findAllByFileName(@PathVariable String fileName) {
        PurchaseMapper mapper = Mappers.getMapper(PurchaseMapper.class);

        List<PurchaseResponse> purchaseResponses = purchaseService.findByFileName(fileName)
                .stream()
                .map(mapper::toPurchaseResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponses);
    }

}
