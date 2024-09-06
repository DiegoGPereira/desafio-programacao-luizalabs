package br.com.magazineluiza.desafio_programacao_luizalabs.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseMapper;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseServiceOperations;
import jakarta.annotation.Nullable;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseServiceOperations purchaseService;
    PurchaseMapper mapper = Mappers.getMapper(PurchaseMapper.class);

    @PostMapping("/upload")
    public ResponseEntity<UUID> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Purchase> purchases;
        purchases = purchaseService.readFile(file);
        purchaseService.saveAll(purchases);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchases.getFirst().getCorrelationId());
    }

    @GetMapping("/gross")
    public ResponseEntity<BigDecimal> gross(@Nullable @RequestParam("correlationId") String correlationId) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.gross(correlationId));
    }

    @GetMapping("/{correlationId}")
    public ResponseEntity<List<PurchaseResponse>> findByCorrelationId(@PathVariable String correlationId) {
        List<PurchaseResponse> purchaseResponses =
                purchaseService.findByCorrelationId(correlationId).stream()
                        .map(purchase -> mapper.toDto(purchase)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponses);
    }

}
