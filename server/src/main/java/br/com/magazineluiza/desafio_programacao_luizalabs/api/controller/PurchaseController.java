package br.com.magazineluiza.desafio_programacao_luizalabs.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseMapper;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseServiceOperations;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseServiceOperations purchaseService;

    @PostMapping("/upload")
    public ResponseEntity<UUID> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Purchase> purchases;
        purchases = purchaseService.readFile(file);
        purchaseService.saveAll(purchases);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchases.getFirst().getCorrelationId());
    }

    @GetMapping("/{correlationId}")
    public ResponseEntity<List<PurchaseResponse>> findByCorrelationId(@PathVariable String correlationId) {
        PurchaseMapper mapper = Mappers.getMapper(PurchaseMapper.class);
        List<PurchaseResponse> purchaseResponses =
                purchaseService.findByCorrelationId(correlationId).stream()
                        .map(mapper::toPurchaseResponse).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponses);
    }

    @GetMapping("/uploaded-files")
    public ResponseEntity<List<PurchaseFilesResponse>> findAllUploadedFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.findAllUploadedFiles());
    }

}
