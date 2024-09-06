package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseServiceOperations extends PurchaseReader<MultipartFile> {

    void saveAll(List<Purchase> file);

    List<Purchase> findAll();

    List<Purchase> findByCorrelationId(String correlationId);

    BigDecimal gross();

}
