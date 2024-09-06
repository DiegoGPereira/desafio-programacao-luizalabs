package br.com.magazineluiza.desafio_programacao_luizalabs.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseServiceOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseServiceOperations purchaseService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Purchase> purchases;
        purchases = purchaseService.readFile(file);
        purchaseService.saveAll(purchases);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping("/gross")
    public ResponseEntity<BigDecimal> grossRevenue() {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.gross());
    }

    @GetMapping("/gross/{correlationId}")
    public ResponseEntity<String> grossRevenue(@PathVariable String correlationId) {
        return ResponseEntity.status(HttpStatus.OK).body(correlationId);
    }

}
