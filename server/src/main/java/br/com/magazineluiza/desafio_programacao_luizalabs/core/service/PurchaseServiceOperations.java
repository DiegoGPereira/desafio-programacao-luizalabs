package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PurchaseServiceOperations extends PurchaseReader<MultipartFile> {

    void saveAll(List<Purchase> purchases);

    List<Purchase> findByCorrelationId(String correlationId);

    List<PurchaseFilesResponse> findAllUploadedFiles();

}
