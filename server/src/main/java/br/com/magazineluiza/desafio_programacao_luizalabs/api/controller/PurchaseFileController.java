package br.com.magazineluiza.desafio_programacao_luizalabs.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileService;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/purchase/file")
public class PurchaseFileController {

    @Autowired
    private PurchaseFileService purchaseFileService;
    @Autowired
    private PurchaseFileServiceFactory purchaseFileServiceFactory;

    @PostMapping
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        purchaseFileService = purchaseFileServiceFactory.getReader(file);
        purchaseFileService.readFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PurchaseFileResponse>> findAllPurchaseFilesSummarized() {
        List<PurchaseFileResponse> purchaseFileResponses =
                purchaseFileService.findAllPurchaseFilesSummarized();

        return ResponseEntity.status(HttpStatus.OK).body(purchaseFileResponses);
    }

}
